package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.service.LoginService;
import com.weiwu.nuclearindustry.utils.LoginParam;
import com.weiwu.nuclearindustry.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public ResultUtil login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
