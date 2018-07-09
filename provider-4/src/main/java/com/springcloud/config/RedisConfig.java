package com.springcloud.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/4/23
 * @Description: redis配置
 **/
@Slf4j
@Data
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.pool.max-active}")
    private Integer maxActive;
    @Value("${spring.redis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private Integer minIdle;
    @Value("${spring.redis.pool.max-wait}")
    private Integer maxWait;

    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 创建redis连接
     * @param host redis地址
     * @param port 端口
     * @param timeout 超时时间
     * @param password 连接redis密码
     * @return
     */
    public JedisConnectionFactory newJedisConnectionFactory(int index, String host, int port, int timeout, String password){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(index);
        factory.setHostName(host);
        factory.setPort(port);
        factory.setTimeout(timeout);
        factory.setPassword(password);
        factory.setPoolConfig(poolCofig(maxWait,minIdle,maxActive,maxWait,true));
        log.info("redis config host :"+host);
        return factory;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //设置key-value超时时间，默认30分钟
        cacheManager.setDefaultExpiration(60 * 30);
        return cacheManager;
    }

    /**
     * @Author: 胡成
     * @Date:   2018/4/26 17:18
     * @Description: redis连接池
     **/
    public JedisPoolConfig poolCofig(int maxIdle, int minIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        poolCofig.setMaxIdle(maxIdle);
        poolCofig.setMinIdle(minIdle);
        poolCofig.setMaxTotal(maxTotal);
        poolCofig.setMaxWaitMillis(maxWaitMillis);
        poolCofig.setTestOnBorrow(testOnBorrow);
        return poolCofig;
    }

    /**
     * @Author: 胡成
     * @Date:   2018/4/27 18:04
     * @Description: 序列化
     **/
    public void setSerializer(RedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
    }

    /**
     * @Author: 胡成
     * @Date:   2018/4/27 18:04
     * @Description: 序列化
     **/
    public void setSerializerCount(RedisTemplate template) {
        //采用自定义key生成策略的时候(也就是下面重写了KeyGenerator生成策略)，需要加上下面自定义key实现并注册到redis模板中
        RedisSerializer<String> serializer = new StringRedisSerializer();
        template.setKeySerializer(serializer);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
    }
}
