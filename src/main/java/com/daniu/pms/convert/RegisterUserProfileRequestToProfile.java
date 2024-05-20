package com.daniu.pms.convert;

import com.daniu.pms.domain.entity.Profile;
import com.daniu.pms.domain.request.RegisterUserProfileRequest;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

/**
 * RegisterUserProfileRequestToProfile
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface RegisterUserProfileRequestToProfile extends BeanConvertMapper<RegisterUserProfileRequest, Profile> {
}
