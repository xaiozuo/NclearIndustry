package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RESTFUL api define
 */
@RestController
@ResponseBody
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * create,update,delete, and return result modified with array
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<User> query(){
        return (List<User>) userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<User> save(User entity){
        userRepository.save(entity);
        return (List<User>) userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public List<User> delete(Long id){
        userRepository.deleteById(id);
        return (List<User>) userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public List<User> update(User entity){
        userRepository.save(entity);
        return (List<User>) userRepository.findAll();
    }
}
