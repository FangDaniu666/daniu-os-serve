package com.daniu.convert;

import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import com.daniu.domain.dto.PermissionDto;
import com.daniu.domain.entity.Permission;
import org.mapstruct.Mapper;

import static com.daniu.common.constant.MapstructConstant.DEFAULT_COMPONENT_MODEL;

/**
 * PermissionToPermissionDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface PermissionToPermissionDto extends BeanConvertMapper<Permission, PermissionDto> {
}
