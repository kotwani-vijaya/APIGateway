package com.gl.product.information;

import java.io.File;
import java.io.IOException;

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

	public static void main(String[] args) {
		String fileLocationPath = args[0];
		String log4j2Path = args[1];
		GetProperties properties = new GetProperties();
		try {
			properties.getProperties(fileLocationPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String location = properties.location();
		File[] listOfFiles = properties.getFiles();
		ProductController productsFile = new ProductController();
		productsFile.setFileLocation(location);
		productsFile.setFiles(listOfFiles);
		productsFile.setLogProperties(log4j2Path);
		SpringApplication.run(App.class, args);
	}

}
