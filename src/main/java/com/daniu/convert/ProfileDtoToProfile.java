package com.daniu.convert;

import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import com.daniu.domain.dto.ProfileDto;
import com.daniu.domain.entity.Profile;
import org.mapstruct.Mapper;

import static com.daniu.common.constant.MapstructConstant.DEFAULT_COMPONENT_MODEL;

/**
 * ProfileDtoToProfile
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface ProfileDtoToProfile extends BeanConvertMapper<ProfileDto, Profile> {
}
