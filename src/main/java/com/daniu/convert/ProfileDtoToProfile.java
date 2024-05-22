package com.daniu.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.domain.dto.ProfileDto;
import com.daniu.domain.entity.Profile;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * ProfileDtoToProfile
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface ProfileDtoToProfile extends BeanConvertMapper<ProfileDto, Profile> {
}
