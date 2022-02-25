package com.liangzhicheng.modules.controller;

import com.liangzhicheng.modules.service.ITestDeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping(value = "/dept")
@RestController
public class DeptController {

    @Resource
    private ITestDeptService deptService;

    @PostMapping("/update")
    public Object update(@RequestParam Long id,
                         @RequestParam String name){
        return deptService.update(id, name);
    }

    @GetMapping("/get/{id}")
    public Object get(@PathVariable("id") Long id){
        return deptService.get(id);
    }

}
