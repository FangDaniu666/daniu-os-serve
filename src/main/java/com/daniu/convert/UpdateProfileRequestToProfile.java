package com.daniu.convert;


import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.domain.entity.Profile;
import com.daniu.domain.request.UpdateProfileRequest;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * UpdateProfileRequestToProfile
 *
 * @author FangDaniu
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface UpdateProfileRequestToProfile extends BeanConvertMapper<UpdateProfileRequest, Profile> {
}
