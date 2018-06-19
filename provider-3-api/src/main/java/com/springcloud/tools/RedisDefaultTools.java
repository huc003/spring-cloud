package com.springcloud.tools;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/6/14
 * @Description: 默认Redis工具类
 **/
@Component
public class RedisDefaultTools extends RedisTools{

    @Resource(name = "defaultRedisTemplate")
    private RedisTemplate defaultRedisTemplate;

    @Override
    RedisTemplate getRedisTemplate() {
        return defaultRedisTemplate;
    }
}
