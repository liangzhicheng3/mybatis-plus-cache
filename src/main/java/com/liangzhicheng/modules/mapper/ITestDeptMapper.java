package com.liangzhicheng.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangzhicheng.config.mybatis.RelationCache;
import com.liangzhicheng.modules.entity.TestDept;
import org.apache.ibatis.annotations.CacheNamespace;

@CacheNamespace(implementation = RelationCache.class, eviction = RelationCache.class, flushInterval = 30 * 60 * 1000)
public interface ITestDeptMapper extends BaseMapper<TestDept> {

}
