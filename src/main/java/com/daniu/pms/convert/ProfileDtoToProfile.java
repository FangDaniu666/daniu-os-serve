package com.daniu.pms.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.pms.domain.dto.ProfileDto;
import com.daniu.pms.domain.entity.Profile;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * ProfileDtoToProfile
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface ProfileDtoToProfile extends BeanConvertMapper<ProfileDto, Profile> {
}
