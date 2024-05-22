package com.daniu.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.domain.dto.RoleDto;
import com.daniu.domain.entity.Role;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * role to roleDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface RoleToRoleDto extends BeanConvertMapper<Role, RoleDto> {

}
