package com.bingco.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;

@SuppressWarnings("unchecked")
public class ShiroCacheManager implements CacheManager {

    private StringRedisTemplate redisTemplate;

    @Override
    public Cache getCache(String s) throws CacheException {
        return new RedisCache(redisTemplate);
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
