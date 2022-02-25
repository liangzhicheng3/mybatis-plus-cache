package com.liangzhicheng.modules.controller;

import com.liangzhicheng.modules.service.ITestUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Resource
    private ITestUserService userService;

    @GetMapping("/get/{id}")
    public Object get(@PathVariable("id") Long id){
        return userService.get(id);
    }

}
