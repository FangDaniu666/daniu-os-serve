package com.daniu.convert;

import static com.daniu.common.constant.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.domain.entity.User;
import com.daniu.domain.request.RegisterUserRequest;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * RegisterUserRequestToUser
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface RegisterUserRequestToUser
    extends BeanConvertMapper<RegisterUserRequest, User> {

}
