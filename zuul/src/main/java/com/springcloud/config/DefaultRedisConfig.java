package com.springcloud.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/4/28
 * @Description: Redis默认配置类
 **/
@Data
@Configuration
public class DefaultRedisConfig extends RedisConfig {

    @Value("${spring.redis.database}")
    private Integer database;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.timeout}")
    private Integer timeout;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * @Author: 胡成
     * @Date: 2018/4/26 17:22
     * @Description: 获取redis连接
     **/
    @Primary
    @Bean
    public JedisConnectionFactory defaultRedisConnectionFactory() {
        return newJedisConnectionFactory(database, host, port, timeout, password);
    }

    /**
     * @Author: 胡成
     * @Date: 2018/4/26 17:24
     * @Description: 初始化默认RedisTemplate
     **/
    @Bean(name = "defaultRedisTemplate")
    public RedisTemplate defaultRedisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(defaultRedisConnectionFactory());
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * @Author: 胡成
     * @Date: 2018/4/26 17:24
     * @Description: 初始化默认StringRedisTemplate
     **/
    @Bean(name = "defaultStringRedisTemplate")
    public StringRedisTemplate defaultStringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(defaultRedisConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * @Author: 胡成
     * @Date: 2018/4/27 18:14
     * @Description: 初始化CountRedisTemplate
     **/
    @Bean(name = "countRedisTemplate")
    public RedisTemplate<String, String> countRedisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(defaultRedisConnectionFactory());
        setSerializerCount(template);
        template.afterPropertiesSet();
        return template;
    }
}
