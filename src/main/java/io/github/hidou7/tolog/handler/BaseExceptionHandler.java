package io.github.hidou7.tolog.handler;

import io.github.hidou7.tolog.exception.BusinessException;
import io.github.hidou7.tolog.exception.LoginException;
import io.github.hidou7.tolog.exception.PermissionException;
import io.github.hidou7.tolog.vo.R;
import io.github.hidou7.tolog.vo.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.OK)
    public R handle(Exception e, HttpServletRequest request, HttpServletResponse response){
        this.logUri(request);
        log.error(e.getMessage(), e);
        return new R<>(StatusCode.internalServerError.getCode(), "未知错误");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R handle(MaxUploadSizeExceededException e, HttpServletRequest request, HttpServletResponse response){
        this.logUri(request);
        StatusCode statusCode = StatusCode.fileTooLarge;
        log.error(statusCode.getMsg(), e);
        return new R<>(statusCode.getCode(), statusCode.getMsg());
    }

    @ExceptionHandler(BusinessException.class)
    public R handle(BusinessException e, HttpServletRequest request, HttpServletResponse response) {
        this.logUri(request);
        log.error(e.getMessage(), e);
        return new R<>(StatusCode.error.getCode(), e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public R handle(LoginException e, HttpServletRequest request, HttpServletResponse response) {
        this.logUri(request);
        StatusCode statusCode = StatusCode.notLogin;
        log.error(statusCode.getMsg());
        return new R<>(statusCode.getCode(), statusCode.getMsg());
    }

    @ExceptionHandler(PermissionException.class)
    public R handle(PermissionException e, HttpServletRequest request, HttpServletResponse response) {
        this.logUri(request);
        StatusCode statusCode = StatusCode.forbidden;
        log.error(statusCode.getMsg());
        return new R<>(statusCode.getCode(), statusCode.getMsg());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handle(HttpRequestMethodNotSupportedException e, HttpServletRequest request, HttpServletResponse response){
        this.logUri(request);
        String msg = e.getMethod() + "方法不被允许";
        log.error(msg);
        return new R<>(StatusCode.error.getCode(), msg);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R handle(HttpMediaTypeNotSupportedException e, HttpServletRequest request, HttpServletResponse response){
        this.logUri(request);
        String msg = "不支持的正文类型：" + e.getContentType();
        log.error(msg, e);
        return new R<>(StatusCode.error.getCode(), msg);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R handle(MethodArgumentTypeMismatchException e, HttpServletRequest request, HttpServletResponse response){
        this.logUri(request);
        String msg = "参数类型不正确：" + e.getName();
        log.error(msg + ", " + request.getServletPath(), e);
        return new R<>(StatusCode.error.getCode(), msg);
    }

    // @RequestParam @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确") String phone
    @ExceptionHandler(ConstraintViolationException.class)
    public R handle(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response){
        this.logUri(request);
        ConstraintViolation constraintViolation = e.getConstraintViolations().toArray(new ConstraintViolation[0])[0];
        String msg = constraintViolation.getMessageTemplate();
        log.error(constraintViolation.getPropertyPath().toString() + ": " + constraintViolation.getInvalidValue() + ", " + msg, e);
        return new R<>(StatusCode.error.getCode(), msg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R handle(HttpMessageNotReadableException e, HttpServletRequest request, HttpServletResponse response){
        this.logUri(request);
        String msg = "无效请求体";
        log.error(msg, e);
        return new R<>(StatusCode.error.getCode(), msg);
    }

    // @RequestParam
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handle(MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) {
        this.logUri(request);
        String msg = "必要请求参数" + e.getParameterName() + "缺失";
        log.error(msg + ", " + request.getServletPath(), e);
        return new R<>(StatusCode.error.getCode(), msg);
    }

    // 数据校验异常 @RequestBody @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handle(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
        this.logUri(request);
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        String msg = fieldError.getDefaultMessage();
        log.error(fieldError.getField() + ": " + fieldError.getRejectedValue() + ", " + msg);
        return new R<>(StatusCode.error.getCode(), msg);
    }

    // 数据校验异常 @Valid
    @ExceptionHandler(BindException.class)
    public R handle(BindException e, HttpServletRequest request, HttpServletResponse response) {
        this.logUri(request);
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        String msg = fieldError.getDefaultMessage();
        log.error(fieldError.getField() + ": " + fieldError.getRejectedValue() + ", " + msg);
        return new R<>(StatusCode.error.getCode(), msg);
    }

    private void logUri(HttpServletRequest request){
        log.error("uri: " + request.getServletPath());
    }
}
