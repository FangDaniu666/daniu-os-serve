package com.daniu.pms.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.pms.domain.dto.PermissionDto;
import com.daniu.pms.domain.entity.Permission;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * PermissionToPermissionDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface PermissionToPermissionDto extends BeanConvertMapper<Permission, PermissionDto> {
}
