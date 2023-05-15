package com.weiwu.nuclearindustry.config;

import com.weiwu.nuclearindustry.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class ResourceConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + systemConfig.getFILE_PATH() + File.separator);
        String[] dataSource = systemConfig.getDATA_SOURCE();
        for(String source : dataSource){
            String sourceId = MD5Util.md5(source);
            String handler = "/source" + sourceId + "/**";
            registry.addResourceHandler(handler)
                    .addResourceLocations("file:" + source + File.separator);
        }
    }
}
