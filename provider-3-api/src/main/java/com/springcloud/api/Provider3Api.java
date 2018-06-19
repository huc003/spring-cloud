package com.springcloud.api;

import com.springcloud.api.config.Provider3FeignConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 胡成
 * @Version: 0.0.1V
 * @Date: 2018/6/15
 * @Description: 类描述
 **/
@FeignClient(value = "provider-3",configuration = Provider3FeignConfig.class)
public interface Provider3Api {
    @GetMapping("/provider-3/hi")
    String sayHi(@RequestParam("name") String name);
}
