package com.gl.product.information;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAutoConfiguration
@EnableEurekaClient
@EnableWebMvc
@EnableHystrix
@SpringBootApplication
@EnableDiscoveryClient
public class App {

	public static void main(String[] args) {
		String fileLocationPath = args[0];
		String log4j2Path = args[1];
		PropertyConfigurator.configure(log4j2Path);
		GetProperties properties = new GetProperties();
		try {
			properties.getProperties(fileLocationPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String location = properties.location();
		File[] listOfFiles = properties.getFiles();
		ProductController productsFile = new ProductController();
		productsFile.setFileLocation(location);
		productsFile.setFiles(listOfFiles);
		SpringApplication.run(App.class, args);
	}

}
