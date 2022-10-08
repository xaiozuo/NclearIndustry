package com.weiwu.nuclearindustry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NuclearIndustryApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NuclearIndustryApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NuclearIndustryApplication.class);
    }
}
