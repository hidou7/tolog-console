package com.hidou7.tolog.vo;

import com.hidou7.tolog.emuns.ErrorCode;
import lombok.Data;

@Data
public class R<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> R<T> success(){
        return success(null);
    }

    public static <T> R<T> success(T data){
        return new R<>(200, "success", data);
    }

    public static <T> R<T> error(Integer code, String message) {
        return new R<>(code, message, null);
    }

    public static <T> R<T> error(ErrorCode errorCode) {
        return new R<>(errorCode.code, errorCode.message, null);
    }

    public static <T> R<T> error(String message) {
        return new R<>(ErrorCode.error.code, message, null);
    }

    private R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
