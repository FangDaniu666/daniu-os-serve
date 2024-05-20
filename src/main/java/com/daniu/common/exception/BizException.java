package com.daniu.common.exception;

import com.daniu.common.response.BizResponseCode;
import lombok.Getter;

/**
 * 业务异常，这种异常一般是可预知的
 *
 * @author FangDaniu
 */
@Getter
public class BizException extends RuntimeException {

    private final BizResponseCode code;

    public BizException(BizResponseCode bizResponseCode) {
        super(bizResponseCode.getMsg());
        this.code = bizResponseCode;
    }

    public BizException(BizResponseCode bizResponseCode, String msg) {
        super(msg);
        this.code = bizResponseCode;
    }

}
