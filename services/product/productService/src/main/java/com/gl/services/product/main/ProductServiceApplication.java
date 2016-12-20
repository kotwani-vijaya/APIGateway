package com.gl.services.product.main;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix
@ComponentScan(basePackages = "com.gl")
@PropertySource("file:/share/config/productService/ProductInformation.properties")
public class ProductServiceApplication {

	public static void main(String[] args) {
		String log4j2Path = args[0];
		PropertyConfigurator.configure(log4j2Path);
		SpringApplication.run(ProductServiceApplication.class, args);

	}
}
