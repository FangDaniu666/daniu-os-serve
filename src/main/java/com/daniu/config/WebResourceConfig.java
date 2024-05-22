package com.daniu.config;

import com.daniu.common.constant.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web资源配置
 *
 * @author FangDaniu
 * @since 2024/05/22
 */
@Configuration
public class WebResourceConfig implements WebMvcConfigurer {
    /**
     * 添加资源位置
     *
     * @param registry 注册
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations(Constants.IMG_PATH);
        registry.addResourceHandler("/song/**")
                .addResourceLocations(Constants.MUSIC_PATH);
        registry.addResourceHandler("/musiccovers/**")
                .addResourceLocations(Constants.COVER_PATH);
    }

}
