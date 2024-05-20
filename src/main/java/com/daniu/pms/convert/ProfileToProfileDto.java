package com.daniu.pms.convert;

import com.daniu.pms.domain.dto.ProfileDto;
import com.daniu.pms.domain.entity.Profile;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

/**
 * profile to profileDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface ProfileToProfileDto extends BeanConvertMapper<Profile, ProfileDto> {

}
