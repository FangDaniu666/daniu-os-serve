package com.daniu.convert;

import com.daniu.domain.entity.Profile;
import com.daniu.domain.request.RegisterUserProfileRequest;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

import static com.daniu.common.constant.MapstructConstant.DEFAULT_COMPONENT_MODEL;

/**
 * RegisterUserProfileRequestToProfile
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface RegisterUserProfileRequestToProfile extends BeanConvertMapper<RegisterUserProfileRequest, Profile> {
}
