package com.daniu.domain.request;

import java.util.List;
import lombok.Data;

/**
 * 给用户分配角色
 *
 * @author FangDaniu
 */
@Data
public class AddUserRolesRequest {

    private List<Long> roleIds;

}
