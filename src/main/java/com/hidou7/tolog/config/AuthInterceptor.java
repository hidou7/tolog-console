package com.hidou7.tolog.config;

import com.hidou7.tolog.annotation.Auth;
import com.hidou7.tolog.emuns.ErrorCode;
import com.hidou7.tolog.exception.BusinessException;
import com.hidou7.tolog.util.TokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    
    @Autowired
    private TokenHelper tokenHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        Auth auth = method.getMethodAnnotation(Auth.class);
        if(auth == null){
            auth = method.getBeanType().getAnnotation(Auth.class);
            if(auth == null){
                return true;
            }
        }
        boolean flag = this.tokenHelper.verify(request.getHeader("token"));
        if(!flag){
            throw new BusinessException(ErrorCode.loginExpired);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
