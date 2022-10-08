package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.service.LoginService;
import com.weiwu.nuclearindustry.utils.LoginParam;
import com.weiwu.nuclearindustry.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins="*",maxAge=3600)
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public ResultUtil login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }
}
