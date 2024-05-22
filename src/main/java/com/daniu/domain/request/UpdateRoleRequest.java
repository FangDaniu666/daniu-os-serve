package com.daniu.domain.request;

import java.util.List;
import lombok.Data;

/**
 * 更新角色
 *
 * @author FangDaniu
 */
@Data
public class UpdateRoleRequest {

    private String name;

    private Boolean enable;

    private List<Long> permissionIds;


}
