package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.entity.TestDept;
import com.liangzhicheng.modules.mapper.ITestDeptMapper;
import com.liangzhicheng.modules.service.ITestDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestDeptServiceImpl extends ServiceImpl<ITestDeptMapper, TestDept> implements ITestDeptService {

    @Override
    public TestDept get(Long id) {
        return baseMapper.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TestDept update(Long id, String name) {
        TestDept dept = baseMapper.selectById(id);
        if(dept != null){
            dept.setName(name);
            baseMapper.updateById(dept);
        }
        return dept;
    }

}
