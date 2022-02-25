package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.TestDept;

public interface ITestDeptService extends IService<TestDept> {

    TestDept get(Long id);

    TestDept update(Long id, String name);

}
