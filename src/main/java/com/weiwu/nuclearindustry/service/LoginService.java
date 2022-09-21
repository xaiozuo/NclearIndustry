package com.weiwu.nuclearindustry.service;

import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.utils.LoginParam;
import com.weiwu.nuclearindustry.utils.ResultUtil;
import org.springframework.stereotype.Component;

@Component
public interface LoginService {
    ResultUtil login(LoginParam loginParam);
}
