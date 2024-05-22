package com.daniu.config;

import cn.dhbin.mapstruct.helper.starter.MapStructHelperAutoConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * mapstruct配置
 *
 * @author FangDaniu
 * @since  2024/05/22
 */
@Configuration
@Import(MapStructHelperAutoConfig.class)
public class MapstructConfig {

}
