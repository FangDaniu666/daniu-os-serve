package com.daniu.pms.domain.dto;

import lombok.Data;

/**
 * 登录响应token
 *
 * @author FangDaniu
 */
@Data
public class LoginTokenDto {

    /**
     * 通过登录获取的token
     */
    private String accessToken;

}
