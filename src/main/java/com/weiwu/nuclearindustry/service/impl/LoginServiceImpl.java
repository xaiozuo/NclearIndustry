package com.weiwu.nuclearindustry.service.impl;

import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.UserRepository;
import com.weiwu.nuclearindustry.service.LoginService;
import com.weiwu.nuclearindustry.utils.LoginParam;
import com.weiwu.nuclearindustry.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultUtil login(LoginParam loginParam) {
        User user = userRepository.findUserByUsername(loginParam.getUsername());
        if(user.getPassword().equals(loginParam.getPassword())){
            return ResultUtil.success("login success", user);
        }
        return ResultUtil.fail("login failed");
    }
}
