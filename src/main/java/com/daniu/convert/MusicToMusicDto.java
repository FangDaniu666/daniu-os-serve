package com.daniu.convert;

import cn.dhbin.mapstruct.helper.core.BeanConvertMapper;
import com.daniu.domain.dto.MusicDto;
import com.daniu.domain.entity.Music;
import org.mapstruct.Mapper;

import static com.daniu.common.constant.MapstructConstant.DEFAULT_COMPONENT_MODEL;

/**
 * music to musicDto
 */
@Mapper(componentModel = DEFAULT_COMPONENT_MODEL)
public interface MusicToMusicDto extends BeanConvertMapper<Music, MusicDto> {

}
