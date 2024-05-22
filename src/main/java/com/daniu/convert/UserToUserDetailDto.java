package com.daniu.convert;

import static com.daniu.common.constant.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.domain.dto.UserDetailDto;
import com.daniu.domain.entity.User;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * user to userDetailDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface UserToUserDetailDto extends BeanConvertMapper<User, UserDetailDto> {

}
