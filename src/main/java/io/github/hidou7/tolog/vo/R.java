package io.github.hidou7.tolog.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class R<T> {

    private Integer code;

    private String message;

    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;

    public static <T> R<T> ok(){
        return ok(null);
    }

    public static <T> R<T> ok(T data){
        StatusCode statusCode = StatusCode.ok;
        return new R<>(statusCode.getCode(), statusCode.getMsg(), data, null);
    }

    public static <T> R<List<T>> page(Page<T> p){
        StatusCode statusCode = StatusCode.ok;
        return new R<>(statusCode.getCode(), statusCode.getMsg(), p.getRecords(), (int) p.getTotal());
    }

    public static <T> R<T> error(String errorMsg) {
        StatusCode statusCode = StatusCode.error;
        return new R<>(statusCode.getCode(), errorMsg, null, null);
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public R(Integer code, String message, T data, Integer total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }
}
