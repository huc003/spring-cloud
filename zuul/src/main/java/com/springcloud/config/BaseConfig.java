package com.springcloud.config;

import com.netflix.loadbalancer.IRule;
import com.springcloud.rule.MyRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/6/14
 * @Description: 自定义负载均衡
 **/
@Configuration
public class BaseConfig {
    @Bean
    public IRule getRule(){
        return new MyRule();
    }
}
