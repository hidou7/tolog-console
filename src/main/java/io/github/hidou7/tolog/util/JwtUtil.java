package io.github.hidou7.tolog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

    private static final Algorithm secretKey = Algorithm.HMAC256("jalkscnjqouwdhmanasd");
    private static final String expireTimestamp = "expireTimestamp";
    private static final Integer expireDuration = 1000 * 60 * 60 * 24;

    /**
     * 生成token并保存用户信息到redis
     */
    public static String createToken(){
        return JWT.create()
                  .withClaim(expireTimestamp, System.currentTimeMillis() + expireDuration)
                  .sign(secretKey);
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
