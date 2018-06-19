package com.springcloud.tools;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/6/15
 * @Description: 类描述
 **/
@Component
public class RedisCountTools extends RedisTools{

    @Resource(name = "countRedisTemplate")
    private RedisTemplate countRedisTemplate;

    @Override
    RedisTemplate getRedisTemplate() {
        return countRedisTemplate;
    }

    public double incr(String key, int by) {
        return countRedisTemplate.boundValueOps(key).increment(by);
    }
}
