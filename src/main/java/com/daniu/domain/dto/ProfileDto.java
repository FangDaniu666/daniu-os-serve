package com.daniu.domain.dto;

import lombok.Data;

/**
 * 用户信息
 *
 * @author FangDaniu
 */
@Data
public class ProfileDto {

    private Long id;

    private Integer gender;

    private String avatar;

    private String address;

    private String email;

    private Long userId;

    private String nickName;

}
