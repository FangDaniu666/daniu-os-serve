package com.daniu.domain.request;

import com.daniu.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页查询
 *
 * @author FangDaniu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageRequest extends PageRequest {

    private String username;

    private Integer gender;

    private Integer role;

    private Boolean enable;


}
