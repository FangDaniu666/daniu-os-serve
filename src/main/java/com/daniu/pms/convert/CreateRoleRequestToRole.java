package com.daniu.pms.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.pms.domain.entity.Role;
import com.daniu.pms.domain.request.CreateRoleRequest;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * CreateRoleRequestToRole
 *
 * @author FangDaniu
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface CreateRoleRequestToRole extends BeanConvertMapper<CreateRoleRequest, Role> {
}
