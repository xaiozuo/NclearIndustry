package com.weiwu.nuclearindustry.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class ImageResourceConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        SystemConfig systemConfig = new SystemConfig();
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + systemConfig.getIMAGE_PATH() + File.separator);
    }
}
