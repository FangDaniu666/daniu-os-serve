package com.daniu.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.domain.dto.RolePageDto;
import com.daniu.domain.entity.Role;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * role to roleDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface RoleToRolePageDto extends BeanConvertMapper<Role, RolePageDto> {

}
