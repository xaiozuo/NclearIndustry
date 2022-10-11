package com.weiwu.nuclearindustry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.logging.Logger;

@SpringBootApplication
public class NuclearIndustryApplication extends SpringBootServletInitializer {

    private static final Logger logger = Logger.getLogger(NuclearIndustryApplication.class.getName());

    public static void main(String[] args) {
        logger.info("application run");
        SpringApplication.run(NuclearIndustryApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NuclearIndustryApplication.class);
    }
}
