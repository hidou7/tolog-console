package io.github.hidou7.tolog.config;

import io.github.hidou7.tolog.annotation.Auth;
import io.github.hidou7.tolog.exception.LoginException;
import io.github.hidou7.tolog.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {


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
        String token = request.getHeader("token");
        if(token == null || !JwtUtil.verify(token)){
            throw new LoginException();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
