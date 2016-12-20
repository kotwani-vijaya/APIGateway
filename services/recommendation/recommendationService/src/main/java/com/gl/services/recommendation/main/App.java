package com.gl.services.recommendation.main;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.gl.services.recommendation.util.FileUtil;

@EnableAutoConfiguration
@EnableEurekaClient
@EnableWebMvc
@EnableHystrix
@SpringBootApplication
@EnableDiscoveryClient
public class App {

	public static void main(String[] args) {
		String filePath = args[0];
		String log4j2Path = args[1];
		PropertyConfigurator.configure(log4j2Path);
		RecommendationController.setRecommendationMap(FileUtil.readRecommendations(filePath));		
		SpringApplication.run(App.class, args);
	}

}
