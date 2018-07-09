package com.springcloud.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/6/19
 * @Description: 类描述
 **/
@FeignClient(value = "provider-5")
public interface Provider5Api {
    @GetMapping("/provider-5/hi")
    String sayHi(@RequestParam("name") String name);
}
