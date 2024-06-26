package com.daniu.common.response;

import com.daniu.common.exception.BusinessException;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一响应body
 *
 * @author FangDaniu
 * @since 2024/05/22
 */
@Data
@Accessors(chain = true)
public class R<T> {

    /**
     * 数据
     */
    private T data;

    /**
     * 响应码
     */
    private int code;

    /**
     * 描述
     */
    private String message;

    /**
     * 无数据的成功响应
     *
     * @param <T> 类型
     * @return 响应体
     */
    public static <T> R<T> ok() {
        return ok(null);
    }

    /**
     * 成功地响应码，附带数据
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应体
     */
    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setData(data);
        r.setCode(ErrorCode.OK.getCode());
        r.setMessage(ErrorCode.OK.getMsg());
        return r;
    }

    /**
     * 成功地响应码，附带消息
     *
     * @param message 消息
     * @return {@link R }<{@link T }>
     */
    public static <T> R<T> ok(String message) {
        R<T> r = new R<>();
        r.setCode(ErrorCode.OK.getCode());
        r.setMessage(message);
        return r;
    }

    /**
     * 成功
     *
     * @param message 消息
     * @param data    数据
     * @return {@link R }<{@link T }>
     */
    public static <T> R<T> success(String message, T data) {
        R<T> r = new R<>();
        r.setData(data);
        r.setCode(ErrorCode.OK.getCode());
        r.setMessage(message);
        return r;
    }

    /**
     * 构建业务异常的响应
     *
     * @param exception 业务异常
     * @param <T>       类型
     * @return 响应体
     */
    public static <T> R<T> build(BusinessException exception) {
        R<T> r = new R<>();
        r.setCode(exception.getCode().getCode());
        r.setData(null);
        r.setMessage(exception.getMessage());
        return r;
    }

    /**
     * 构建错误响应
     *
     * @param message 消息
     * @return {@link R }<{@link T }>
     */
    public static <T> R<T> error(String message) {
        R<T> r = new R<>();
        r.setCode(ErrorCode.ERR_500.getCode());
        r.setData(null);
        r.setMessage(message);
        return r;
    }

}
