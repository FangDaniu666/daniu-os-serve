package com.daniu.common.response;

import lombok.Data;

/**
 * 统一响应
 *
 * @author FangDaniu
 * @since  2024/05/22
 */
@Data
public class Result {

    private int code;

    private String message;

    private String type;

    private Boolean success;

    private Object data;

    /**
     * 成功
     *
     * @param message 消息
     * @return {@link Result }
     */
    public static Result success(String message) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage(message);
        result.setSuccess(true);
        result.setType("success");
        result.setData(null);
        return result;
    }

    /**
     * 成功
     *
     * @param message 消息
     * @param data    数据
     * @return {@link Result }
     */
    public static Result success(String message, Object data) {
        Result result = success(message);
        result.setData(data);
        return result;
    }

    /**
     * 警告
     *
     * @param message 消息
     * @return {@link Result }
     */
    public static Result warning(String message) {
        Result result = error(message);
        result.setType("warning");
        return result;
    }

    /**
     * 错误
     *
     * @param message 消息
     * @return {@link Result }
     */
    public static Result error(String message) {
        Result result = success(message);
        result.setSuccess(false);
        result.setType("error");
        return result;
    }

}
