package com.daniu.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.daniu.common.response.ErrorCode;
import com.daniu.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author FangDaniu
 * @since 2024/05/22
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理在没有登录请求的异常
     *
     * @param exception NotLoginException
     * @return 返回401的http状态码
     */
    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<R<String>> handle(NotLoginException exception) {
        log.debug("", exception);
        R<String> r = new R<>();
        r.setCode(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(r);
    }

    /**
     * 处理业务异常
     *
     * @param exception BusinessException
     * @return 返回200的http状态码，body描述异常信息
     */
    @ResponseBody
    @ExceptionHandler
    public R<Object> handle(BusinessException exception) {
        return R.build(exception);
    }

    /**
     * 处理参数错误的异常
     *
     * @param exception BadRequestException
     * @return 返回 400 状态码
     */
    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<R<String>> handle(BadRequestException exception) {
        R<String> r = new R<>();
        r.setMessage(exception.getMessage()).setData(null).setCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
    }


    /**
     * 处理角色无权操作异常
     *
     * @param exception ex
     * @return {@link R }<{@link String }>
     */
    @ResponseBody
    @ExceptionHandler
    public R<String> handle(NotRoleException exception) {
        log.debug("当前用户角色无权操作", exception);
        ErrorCode code = ErrorCode.ERR_11003;
        R<String> r = new R<>();
        r.setMessage(code.getMsg())
            .setCode(code.getCode());
        return r;
    }

    /**
     * 处理参数错误异常
     *
     * @param exception ex
     * @return {@link R }<{@link String }>
     */
    @ResponseBody
    @ExceptionHandler
    public R<String> handle(MethodArgumentNotValidException exception) {
        ObjectError error = exception.getBindingResult().getAllErrors().get(0);
        String defaultMessage = error.getDefaultMessage();
        return R.build(new BusinessException(ErrorCode.ERR_400, defaultMessage));
    }

}
