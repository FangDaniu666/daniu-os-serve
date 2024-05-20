package com.daniu.pms.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.pms.domain.entity.User;
import com.daniu.pms.domain.request.RegisterUserRequest;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * RegisterUserRequestToUser
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface RegisterUserRequestToUser
    extends BeanConvertMapper<RegisterUserRequest, User> {

}
