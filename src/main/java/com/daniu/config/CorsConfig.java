package com.daniu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") // 允许所有来源
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // 允许的方法
                        .allowedHeaders("*") // 允许的请求头
                        //.allowCredentials(true) // 是否允许发送Cookie
                        .maxAge(3600); // 预检请求的有效期，单位秒
            }
        };
    }
}

