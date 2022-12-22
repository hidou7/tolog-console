package io.github.hidou7.tolog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.hidou7.tolog.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

    private static final Algorithm secretKey = Algorithm.HMAC256("jalkscnjqouwdhmanasd");
    private static final String expireTimestamp = "expireTimestamp";
    private static final Integer expireDuration = 1000 * 60 * 60 * 24;

    /**
     * 生成token并保存用户信息到redis
     */
    public static LoginVo createToken(){
        Long expire = System.currentTimeMillis() + expireDuration;
        String token =  JWT.create()
                .withClaim(expireTimestamp, expire)
                .sign(secretKey);
        return new LoginVo(token, expire);
    }

    public static boolean verify(String token){
        try{
            JWTVerifier jwtVerifier = JWT.require(secretKey).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Long expireTime = decodedJWT.getClaim(expireTimestamp).asLong();
            if(expireTime > System.currentTimeMillis()){
                return true;
            }
        }catch (Exception e){
            log.error("token解析失败: " + token, e);
        }
        return false;
    }
}
