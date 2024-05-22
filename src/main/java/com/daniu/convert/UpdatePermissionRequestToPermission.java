package com.daniu.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.domain.entity.Permission;
import com.daniu.domain.request.UpdatePermissionRequest;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * UpdatePermissionRequestToPermission
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface UpdatePermissionRequestToPermission
    extends BeanConvertMapper<UpdatePermissionRequest, Permission> {

}
