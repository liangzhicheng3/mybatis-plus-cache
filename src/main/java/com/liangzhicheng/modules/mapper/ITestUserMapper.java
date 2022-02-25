package com.liangzhicheng.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangzhicheng.config.mybatis.RelationCache;
import com.liangzhicheng.config.mybatis.annotation.SecondCache;
import com.liangzhicheng.modules.entity.TestUser;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

@SecondCache(from = ITestDeptMapper.class)
@CacheNamespace(implementation = RelationCache.class, eviction = RelationCache.class, flushInterval = 30 * 60 * 1000)
public interface ITestUserMapper extends BaseMapper<TestUser> {

    TestUser get(@Param("userId") Long userId);

}
