package com.daniu.pms.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.pms.domain.entity.Permission;
import com.daniu.pms.domain.request.CreatePermissionRequest;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * CreatePermissionRequestToPermission
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface CreatePermissionRequestToPermission
    extends BeanConvertMapper<CreatePermissionRequest, Permission> {

}
