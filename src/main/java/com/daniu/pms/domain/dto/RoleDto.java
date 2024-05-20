package com.daniu.pms.domain.dto;

import lombok.Data;

/**
 * 角色Dto
 *
 * @author FangDaniu
 */
@Data
public class RoleDto {

    private Long id;

    private String code;

    private String name;

    private Boolean enable;

}
