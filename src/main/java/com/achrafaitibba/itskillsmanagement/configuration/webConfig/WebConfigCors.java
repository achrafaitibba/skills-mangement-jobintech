package com.achrafaitibba.itskillsmanagement.configuration.webConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigCors implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry coreRegistry) {
        coreRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*");
    }
}
