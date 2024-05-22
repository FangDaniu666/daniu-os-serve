package com.daniu.convert;

import com.daniu.domain.dto.ProfileDto;
import com.daniu.domain.entity.Profile;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

import static com.daniu.common.constant.MapstructConstant.DEFAULT_COMPONENT_MODEL;

/**
 * profile to profileDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface ProfileToProfileDto extends BeanConvertMapper<Profile, ProfileDto> {

}
