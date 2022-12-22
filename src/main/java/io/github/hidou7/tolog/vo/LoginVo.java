package io.github.hidou7.tolog.vo;

import lombok.Data;

@Data
public class LoginVo {

    private String token;

    private Long expire;

    public LoginVo() {
    }

    public LoginVo(String token, Long expire) {
        this.token = token;
        this.expire = expire;
    }
}
