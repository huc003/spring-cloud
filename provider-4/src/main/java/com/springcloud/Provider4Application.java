package com.springcloud;

import com.springcloud.api.Provider3Api;
import com.springcloud.api.Provider5Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Provider4Application {

	public static void main(String[] args) {
		SpringApplication.run(Provider4Application.class, args);
	}

	@Value("${server.port}")
	private String port;

	@Autowired
	private Provider3Api provider3Api;

	@Autowired
	private Provider5Api provider5Api;

	@RequestMapping("/hi")
	public String sayHi(@RequestParam String name) {
		System.out.println("provider-3  -->  "+name);
		provider3Api.sayHi(name);
		return "hi " + name + ",i am from port:" + port;
	}


	@RequestMapping("/hello")
	public String sayHello(@RequestParam String name) {
		System.out.println("provider-5  -->  "+name);
		provider5Api.sayHi(name);
		return "hi " + name + ",i am from port:" + port;
	}
}
