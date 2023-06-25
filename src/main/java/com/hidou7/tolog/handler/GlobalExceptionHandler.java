package com.hidou7.tolog.handler;

import com.hidou7.tolog.exception.BusinessException;
import com.hidou7.tolog.vo.R;
import com.hidou7.tolog.emuns.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
@ResponseStatus(HttpStatus.OK)
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public R handle(Exception e, HttpServletRequest request, HttpServletResponse response){
        log.error(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.internalError;
        return R.error(errorCode.code, errorCode.message);
    }
    
    // 业务异常
    @ExceptionHandler(BusinessException.class)
    public R handle(BusinessException e, HttpServletRequest request, HttpServletResponse response){
        log.error(e.getMessage());
        return R.error(e.getCode(), e.getMessage());
    }
    
    // 数据校验异常 @Valid
    @ExceptionHandler(BindException.class)
    public R handleException(BindException e, HttpServletRequest request, HttpServletResponse response) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        String msg = fieldError.getDefaultMessage();
        this.logWithUri(fieldError.getField() + ": " + fieldError.getRejectedValue() + ", " + msg, request);
        return R.error(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R handleException(HttpMessageNotReadableException e, HttpServletRequest request, HttpServletResponse response){
        String msg = "无效请求体";
        this.logWithUri(msg, request);
        return R.error(msg);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleException(HttpRequestMethodNotSupportedException e, HttpServletRequest request, HttpServletResponse response){
        String msg = e.getMethod() + "方法不被允许";
        this.logWithUri(msg, request);
        return R.error(msg);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R handleException(HttpMediaTypeNotSupportedException e, HttpServletRequest request, HttpServletResponse response){
        String msg = "不支持的正文类型：" + e.getContentType().toString().split(";")[0];
        this.logWithUri(msg, request);
        return R.error(msg);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R handleException(MethodArgumentTypeMismatchException e, HttpServletRequest request, HttpServletResponse response){
        String msg = "参数类型错误";
        this.logWithUri(e.getName() + " " + msg, request);
        return R.error(msg);
    }

    // @Validated @RequestParam @NotBlank
    @ExceptionHandler(ConstraintViolationException.class)
    public R handleException(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response){
        ConstraintViolation constraintViolation = e.getConstraintViolations().toArray(new ConstraintViolation[0])[0];
        String msg = constraintViolation.getMessage();
        String[] split = constraintViolation.getPropertyPath().toString().split("\\.");
        this.logWithUri(split[split.length - 1] + ": " + constraintViolation.getInvalidValue() + ", " + msg, request);
        return R.error(msg);
    }

    // @RequestParam
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handleException(MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) {
        String msg = "请求参数缺失";
        this.logWithUri(e.getParameterName() + " " + msg, request);
        return R.error(msg);
    }
    
    private void logWithUri(String msg, HttpServletRequest request){
        log.error(msg + ", uri: " + request.getServletPath());
    }
}
