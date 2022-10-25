package com.weiwu.nuclearindustry.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class ImageResourceConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + systemConfig.getIMAGE_PATH() + File.separator);
    }
}
