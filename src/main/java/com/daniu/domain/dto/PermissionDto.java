package com.daniu.domain.dto;

import lombok.Data;

/**
 * 权限
 *
 * @author FangDaniu
 */
@Data
public class PermissionDto {

    private Long id;

    private String name;

    private String code;

    private String type;

    private Long parentId;

    private String path;

    private String redirect;

    private String icon;

    private String component;

    private String layout;

    private Boolean keepalive;

    private String method;

    private String description;

    private Boolean show;

    private Boolean enable;

    private Integer order;

}
