package com.daniu.convert;

import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import com.daniu.domain.entity.Role;
import com.daniu.domain.request.CreateRoleRequest;
import org.mapstruct.Mapper;

import static com.daniu.common.constant.MapstructConstant.DEFAULT_COMPONENT_MODEL;

/**
 * CreateRoleRequestToRole
 *
 * @author FangDaniu
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface CreateRoleRequestToRole extends BeanConvertMapper<CreateRoleRequest, Role> {
}
