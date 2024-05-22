package com.daniu.convert;

import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import com.daniu.domain.entity.Permission;
import com.daniu.domain.request.CreatePermissionRequest;
import org.mapstruct.Mapper;

import static com.daniu.common.constant.MapstructConstant.DEFAULT_COMPONENT_MODEL;

/**
 * CreatePermissionRequestToPermission
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface CreatePermissionRequestToPermission
    extends BeanConvertMapper<CreatePermissionRequest, Permission> {

}
