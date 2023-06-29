package com.hidou7.tolog.controller;

import com.hidou7.tolog.config.UserInfoProperties;
import com.hidou7.tolog.dto.LoginDto;
import com.hidou7.tolog.exception.BusinessException;
import com.hidou7.tolog.util.TokenHelper;
import com.hidou7.tolog.vo.LoginVo;
import com.hidou7.tolog.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 登录管理
 */
@Controller
public class LoginController {

    @Autowired
    private UserInfoProperties userInfoProperties;
    @Autowired
    private TokenHelper tokenHelper;

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @PostMapping("login")
    @ResponseBody
    public R<LoginVo> login(@RequestBody @Valid LoginDto dto){
        if(dto.getUsername().equals(this.userInfoProperties.getUsername()) && dto.getPassword().equals(this.userInfoProperties.getPassword())){
            return R.success(this.tokenHelper.createToken());
        }
        throw new BusinessException("账号或密码错误");
    }
}
