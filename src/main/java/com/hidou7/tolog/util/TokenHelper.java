package com.hidou7.tolog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hidou7.tolog.emuns.ErrorCode;
import com.hidou7.tolog.exception.BusinessException;
import com.hidou7.tolog.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Slf4j
@Component
public class TokenHelper {
    
    private static final String expireTimestamp = "expireTimestamp";
    private static final Integer expireDuration = 1000 * 60 * 60 * 24;

    private Algorithm algorithm;

    @Value("${token.secretKey}") 
    private String secretKey;
    
    @PostConstruct
    public void init(){
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    /**
     * 生成token并保存用户信息到redis
     */
    public LoginVo createToken(){
        Long expire = System.currentTimeMillis() + expireDuration;
        String token =  JWT.create()
                .withClaim(expireTimestamp, expire)
                .sign(this.algorithm);
        return new LoginVo(token, expire);
    }

    public boolean verify(String token){
        try{
            JWTVerifier jwtVerifier = JWT.require(this.algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Long expireTime = decodedJWT.getClaim(expireTimestamp).asLong();
            if(expireTime > System.currentTimeMillis()){
                return true;
            }
        }catch (Exception ignored){
        
        }
        return false;
    }
}
