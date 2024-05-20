package com.daniu.pms.domain.request;

import java.util.List;
import lombok.Data;

/**
 * 给角色分配用户
 *
 * @author FangDaniu
 */
@Data
public class RemoveRoleUsersRequest {

    private List<Long> userIds;

}
