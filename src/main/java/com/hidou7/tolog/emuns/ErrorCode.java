package com.hidou7.tolog.emuns;

public enum ErrorCode {

    error(1000, "错误"),
    loginExpired(401, "登录过期"),
    forbidden(403, "权限不足"),
    internalError(500, "内部错误");

    public final Integer code;
    public final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
