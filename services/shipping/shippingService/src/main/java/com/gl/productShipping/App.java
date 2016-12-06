package com.gl.productShipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableEurekaClient
@EnableWebMvc
@SpringBootApplication
@EnableDiscoveryClient
public class App {

	public static void main(String[] args) throws Exception {
		
		String logPath=args[0];
		
		ShippingController.setLogProperties(logPath);
		
		ShippingController.shippingController();
		
		ShippingController.customerController();
		
		ShippingController.availableController();
		
		SpringApplication.run(App.class, args);
	}
}
