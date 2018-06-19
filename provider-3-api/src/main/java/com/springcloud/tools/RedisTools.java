package com.springcloud.tools;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/6/14
 * @Description: Redis基类
 **/
public abstract class RedisTools {

    abstract RedisTemplate getRedisTemplate();

    public Long lpush(String key,String value){
        return getRedisTemplate().opsForList().leftPush(key, value);
    }

    public Object pop(String key){
        return getRedisTemplate().opsForList().leftPop(key);
    }

    public Long rpush(String key,String value){
        return getRedisTemplate().opsForList().rightPush(key, value);
    }

    public Object out(String key){
        return getRedisTemplate().opsForList().rightPop(key);
    }

    public Long length(String key){
        return getRedisTemplate().opsForList().size(key);
    }

    public List<String> range(String key, int start, int end){
        return getRedisTemplate().opsForList().range(key, start, end);
    }

    public void remove(String key,int count,String value){
        getRedisTemplate().opsForList().remove(key, count, value);
    }

    public Object index(String key,long index){
        return getRedisTemplate().opsForList().index(key, index);
    }

    public void set(String key,long index,String value){
        getRedisTemplate().opsForList().set(key, index, value);
    }

    public void trim(String key,long start,long end){
        getRedisTemplate().opsForList().trim(key, start, end);
    }

    public void clean(String key){
        getRedisTemplate().delete(key);
    }

    public Object get(final String key) {
        return getRedisTemplate().opsForValue().get(key);
    }

}
