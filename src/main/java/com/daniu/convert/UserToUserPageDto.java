package com.daniu.convert;

import static com.daniu.common.mapstruct.MapstructConstant.DEFAULT_COMPONENT_MODEL;

import com.daniu.domain.dto.UserPageDto;
import com.daniu.domain.entity.User;
import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import org.mapstruct.Mapper;

/**
 * user to UserPageDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface UserToUserPageDto extends BeanConvertMapper<User, UserPageDto> {

}
