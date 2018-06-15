package com.springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class Provider3Application {

	public static void main(String[] args) {
		SpringApplication.run(Provider3Application.class, args);
	}

	@Value("${server.port}")
	private String port;

	@RequestMapping("/hi")
	public String sayHi(@RequestParam String name) {
		System.out.println("provider-3  -->  "+name);
		return "hi " + name + ",i am from port:" + port;
	}
}
