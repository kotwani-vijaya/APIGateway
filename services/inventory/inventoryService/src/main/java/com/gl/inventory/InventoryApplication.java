package com.gl.inventory;

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

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration
@EnableEurekaClient
@EnableWebMvc
@EnableHystrix
@SpringBootApplication
@EnableDiscoveryClient
public class InventoryApplication 
{
    public static void main( String[] args )
    {
    	String inventoryPropertyPath = args[0];
		String log4j2Path = args[1];
		PropertyConfigurator.configure(log4j2Path);
		GetInventoryProperties properties = new GetInventoryProperties();
		try {
			properties.getProperties(inventoryPropertyPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String detailsLocation = properties.inventoryDetailLocation();
		File[] listOfFiles = properties.getInventoryDeatilFiles();
		String inventoryLocation = properties.inventoryLocation();
		InventoryController fetchingFiles = new InventoryController();
		fetchingFiles.setFileLocation(detailsLocation);
		fetchingFiles.settingInventory(inventoryLocation);
		fetchingFiles.setFiles(listOfFiles);		
    	SpringApplication.run(InventoryApplication.class, args);
    }
}
