package com.gl.services.shipping.main;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.gl.services.shipping.controller.ShippingController;

@EnableAutoConfiguration
@EnableEurekaClient
@EnableWebMvc
@EnableHystrix
@SpringBootApplication
@EnableDiscoveryClient
public class App {

	public static void main(String[] args) throws Exception {
		
		String log4j2Path=args[0];
		PropertyConfigurator.configure(log4j2Path);
			
		ShippingController.shippingController();
		
		ShippingController.customerController();
				
		SpringApplication.run(App.class, args);
	}
}
