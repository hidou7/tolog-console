package com.hidou7.tolog.exception;

import com.hidou7.tolog.emuns.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    private Integer code = ErrorCode.error.code;

    public BusinessException(ErrorCode errorCode){
        this(errorCode.message);
        this.code = errorCode.code;
    }

    public BusinessException(String message) {
        super(message);
    }
}
