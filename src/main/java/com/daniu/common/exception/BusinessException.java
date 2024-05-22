package com.daniu.common.exception;

import com.daniu.common.response.ErrorCode;
import lombok.Getter;

/**
 * 业务异常，这种异常一般是可预知的
 *
 * @author FangDaniu
 * @since  2024/05/22
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode code;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String msg) {
        super(msg);
        this.code = errorCode;
    }

}
