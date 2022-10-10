package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.config.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private SystemConfig systemConfig;

    @RequestMapping(method = RequestMethod.GET)
    public String testConfig(){
        String path = Arrays.toString(systemConfig.getDATA_SOURCE()) + " : " + systemConfig.getUNTARGZ_PATH() + " : " +
                systemConfig.getIMAGE_PATH();
        return path;
    }
}
