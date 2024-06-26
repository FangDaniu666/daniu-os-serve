package com.daniu.domain.dto;

import java.util.List;
import lombok.Data;

/**
 * 角色Dto
 *
 * @author FangDaniu
 */
@Data
public class RolePageDto {

    private Long id;

    private String code;

    private String name;

    private Boolean enable;

    private List<Long> permissionIds;

}
