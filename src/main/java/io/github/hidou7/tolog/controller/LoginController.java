package io.github.hidou7.tolog.controller;

import io.github.hidou7.tolog.config.UserInfoProperties;
import io.github.hidou7.tolog.dto.LoginDto;
import io.github.hidou7.tolog.exception.BusinessException;
import io.github.hidou7.tolog.util.JwtUtil;
import io.github.hidou7.tolog.vo.LoginVo;
import io.github.hidou7.tolog.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserInfoProperties userInfoProperties;

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @PostMapping("login")
    @ResponseBody
    public R<LoginVo> login(@RequestBody @Valid LoginDto dto){
        if(dto.getUsername().equals(this.userInfoProperties.getUsername()) && dto.getPassword().equals(this.userInfoProperties.getPassword())){
            return R.ok(JwtUtil.createToken());
        }
        throw new BusinessException("账号或密码错误");
    }
}
