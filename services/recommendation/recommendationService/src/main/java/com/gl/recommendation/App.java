package com.gl.recommendation;

import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.gl.recommendation.util.FileUtil;

@EnableEurekaClient
@EnableWebMvc
@SpringBootApplication
@EnableDiscoveryClient
public class App {

	public static void main(String[] args) {
		String filePath = args[0];
		String log4j2Path = args[1];
		RecommendationController.setRecommendationMap(FileUtil.readRecommendations(filePath));
		RecommendationController controller = new RecommendationController();		
		controller.setLogProperties(log4j2Path);
		SpringApplication.run(App.class, args);
	}

}
