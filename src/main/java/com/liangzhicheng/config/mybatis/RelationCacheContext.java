package com.liangzhicheng.config.mybatis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RelationCacheContext {

    /**
     * 存储全量缓存的映射关系
     */
    public static final Map<Class<?>, RelationCache> MAPPER_CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 存储Mapper对应缓存，需要to更新缓存，但是此时Mapper对应缓存还未加载
     * 也就是Class<?>对应的缓存更新时，需要更新List<RelationCache>中的缓存
     */
    public static final Map<Class<?>, List<RelationCache>> UN_LOAD_TO_RELATION_CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 存储Mapper对应缓存，需要from更新缓存，但是在加载Mapper缓存时，这些缓存还未加载
     * 也就是List<RelationCache>中的缓存更新时，需要更新Class<?>对应的缓存
     */
    public static final Map<Class<?>, List<RelationCache>> UN_LOAD_FROM_RELATION_CACHE_MAP = new ConcurrentHashMap<>();

    public static void putCache(Class<?> clazz, RelationCache cache) {
        MAPPER_CACHE_MAP.put(clazz, cache);
    }

    public static void getCache(Class<?> clazz) {
        MAPPER_CACHE_MAP.get(clazz);
    }

}
