package io.github.hidou7.tolog.vo;

import lombok.Getter;

@Getter
public enum StatusCode {

    ok(200, "成功"),
    error(1000, "失败"),
    notLogin(401, "登录过期"),
    forbidden(403, "权限不足"),
    internalServerError(500, "服务端错误"),
    dbBusy(5004, "数据库繁忙"),
    fileTooLarge(4011, "文件过大"),
    thirdServiceFail(5111, "第三方服务调用失败"),
    uniqueConstraint(1111, "唯一约束异常");

    private final Integer code;
    private final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
