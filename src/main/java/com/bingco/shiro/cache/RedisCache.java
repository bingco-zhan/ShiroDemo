package com.bingco.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisCache<K, V> implements Cache {

    private final StringRedisTemplate redisTemplate;

    private Long expire = 1800L;

    public RedisCache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisCache(Long expire, StringRedisTemplate redisTemplate) {
        this.expire = expire;
        this.redisTemplate = redisTemplate;
    }

    private RedisConnection getConnection() {
        return redisTemplate.getConnectionFactory().getConnection();
    }

    @Override
    public Object get(Object key) throws CacheException {
        RedisConnection connection = getConnection();
        byte[] serializeKey = SerializationUtils.serialize(key);
        byte[] bytes = connection.get(serializeKey);
        connection.expire(serializeKey, expire);
        connection.close();
        Object deserialize = SerializationUtils.deserialize(bytes);
        return bytes != null && bytes.length > 0 ? deserialize : null;
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        RedisConnection connection = getConnection();
        byte[] serializeKey = SerializationUtils.serialize(key);
        connection.set(serializeKey, SerializationUtils.serialize(value));
        connection.expire(serializeKey, expire);
        connection.close();
        return value;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        RedisConnection connection = getConnection();
        byte[] serialize = SerializationUtils.serialize((Serializable) key);
        byte[] bytes = connection.get(serialize);
        Long del = connection.del(serialize);
        connection.close();
        return del > 0 ? SerializationUtils.deserialize(bytes) : null;
    }

    @Override
    public void clear() throws CacheException {
        RedisConnection connection = getConnection();
        connection.flushDb();
        connection.close();
    }

    @Override
    public int size() {
        RedisConnection connection = getConnection();
        Long size = connection.dbSize();
        connection.close();
        return size.intValue();
    }

    @Override
    public Set<Object> keys() {
        RedisConnection connection = getConnection();
        Set<byte[]> keys = connection.keys("*".getBytes());
        Set<Object> result = new HashSet<>();
        keys.forEach((by) -> result.add(SerializationUtils.deserialize(by)));
        connection.close();
        return result;
    }

    @Override
    public Collection<Object> values() {
        RedisConnection connection = getConnection();
        Set<byte[]> keys = connection.keys("*".getBytes());
        Set<Object> result = new HashSet<>();
        keys.forEach((by) -> result.add(SerializationUtils.deserialize(connection.get(by))));
        connection.close();
        return result;
    }
}
