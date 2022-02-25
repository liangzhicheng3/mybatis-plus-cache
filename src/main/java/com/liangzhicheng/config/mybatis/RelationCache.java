package com.liangzhicheng.config.mybatis;

import com.liangzhicheng.config.mybatis.annotation.SecondCache;
import org.apache.ibatis.cache.Cache;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RelationCache implements Cache {

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private Map<Object, Object> cacheMap = new ConcurrentHashMap<>();

    private List<RelationCache> relationCacheList = new ArrayList<>();

    private String id;
    private Class<?> clazzMap;
    private boolean clear;

    public RelationCache(String id) throws Exception {
        this.id = id;
        this.clazzMap = Class.forName(id);
        RelationCacheContext.putCache(clazzMap, this);
        loadRelationCache();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        cacheMap.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cacheMap.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return cacheMap.remove(key);
    }

    @Override
    public void clear() {
        ReadWriteLock readWriteLock = getReadWriteLock();
        Lock lock = readWriteLock.writeLock();
        lock.lock();
        try {
            /**
             * 判断当前缓存是否正在清空，如果正在清空，取消本次操作
             * 避免缓存出现循环relation，造成递归无终止，调用栈溢出
             */
            if (clear) {
                return;
            }
            clear = true;
            try {
                cacheMap.clear();
                relationCacheList.forEach(RelationCache::clear);
            } finally {
                clear = false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getSize() {
        return cacheMap.size();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    void loadRelationCache() {
        /**
         * 加载其他缓存更新时，需要更新此缓存的caches
         * 将此缓存加入至这些caches的relations中
         */
        List<RelationCache> to = RelationCacheContext.UN_LOAD_TO_RELATION_CACHE_MAP.get(clazzMap);
        if (to != null) {
            to.forEach(relationCache -> this.addRelation(relationCache));
        }
        /**
         * 加载此缓存更新时，需要更新的一些缓存caches
         * 将这些缓存caches加入至此缓存relations中
         */
        List<RelationCache> from = RelationCacheContext.UN_LOAD_FROM_RELATION_CACHE_MAP.get(clazzMap);
        if (from != null) {
            from.forEach(relationCache -> relationCache.addRelation(this));
        }
        SecondCache secondCache = AnnotationUtils.findAnnotation(clazzMap, SecondCache.class);
        if (secondCache == null) {
            return;
        }
        Class<?>[] toMappers = secondCache.to();
        Class<?>[] fromMappers = secondCache.from();
        if (toMappers != null && toMappers.length > 0) {
            for (Class c : toMappers) {
                RelationCache relationCache = RelationCacheContext.MAPPER_CACHE_MAP.get(c);
                if (relationCache != null) {
                    //将找到的缓存添加到当前缓存的relations中
                    this.addRelation(relationCache);
                } else {
                    /**
                     * 如果找不到to cache，证明to cache还未加载，这时需将对应关系存放到UN_LOAD_FROM_RELATIVE_CACHES_MAP
                     * 也就是说c对应cache需要在当前缓存更新时，进行更新
                     */
                    List<RelationCache> list = RelationCacheContext.UN_LOAD_FROM_RELATION_CACHE_MAP.putIfAbsent(c, new ArrayList<RelationCache>());
                    list.add(this);
                }
            }
        }
        if (fromMappers != null && fromMappers.length > 0) {
            for (Class c : fromMappers) {
                RelationCache relationCache = RelationCacheContext.MAPPER_CACHE_MAP.get(c);
                if (relationCache != null) {
                    //将找到的缓存添加到当前缓存的relations中
                    relationCache.addRelation(this);
                } else {
                    /**
                     * 如果找不到from cache，证明from cache还未加载，这时需将对应关系存放到UN_LOAD_TO_RELATIVE_CACHES_MAP
                     * 也就是说c对应cache更新时，需要更新当前缓存
                     */
                    List<RelationCache> list = RelationCacheContext.UN_LOAD_TO_RELATION_CACHE_MAP.putIfAbsent(c, new ArrayList<RelationCache>());
                    list.add(this);
                }
            }
        }
    }

    public void addRelation(RelationCache relation) {
        if (relationCacheList.contains(relation)){
            return;
        }
        relationCacheList.add(relation);
    }

}
