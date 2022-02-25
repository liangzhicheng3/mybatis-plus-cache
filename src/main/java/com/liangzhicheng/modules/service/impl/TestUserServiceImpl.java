package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.entity.TestUser;
import com.liangzhicheng.modules.mapper.ITestUserMapper;
import com.liangzhicheng.modules.service.ITestUserService;
import org.springframework.stereotype.Service;

@Service
public class TestUserServiceImpl extends ServiceImpl<ITestUserMapper, TestUser> implements ITestUserService {

    @Override
    public TestUser get(Long userId) {
        return baseMapper.get(userId);
    }

}
