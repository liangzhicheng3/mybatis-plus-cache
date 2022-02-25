package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.TestUser;

public interface ITestUserService extends IService<TestUser> {

    TestUser get(Long userId);

}
