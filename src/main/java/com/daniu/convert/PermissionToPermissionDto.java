package com.daniu.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.domain.dto.PermissionDto;
import com.daniu.domain.entity.Permission;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * PermissionToPermissionDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface PermissionToPermissionDto extends BeanConvertMapper<Permission, PermissionDto> {
}
