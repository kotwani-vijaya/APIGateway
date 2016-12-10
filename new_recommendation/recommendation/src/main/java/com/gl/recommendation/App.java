package com.gl.recommendation;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gl.recommendation.util.FileUtil;

@EnableAutoConfiguration
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		String filePath = args[0];
		String log4j2Path = args[1];
		PropertyConfigurator.configure(log4j2Path);
		RecommendationController.setRecommendationMap(FileUtil.readRecommendations(filePath));		
		SpringApplication.run(App.class, args);
	}

}
