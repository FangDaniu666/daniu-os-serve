package com.daniu.domain.request;

import com.daniu.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageRequest extends PageRequest {

    private String name;

    private Boolean enable;

}
