package com.gl.customer;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableEurekaClient
@EnableWebMvc
@EnableHystrix
@SpringBootApplication
@EnableDiscoveryClient
public class CustomerApplication 
{
    public static void main( String[] args )
    {
    	String customerProp = args[0];
    	String log4j2Path = args[1];
    	PropertyConfigurator.configure(log4j2Path);
    	GetCustomerProperties properties = new GetCustomerProperties();
    	try {
			properties.getProperties(customerProp);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	String location = properties.location();
    	File[] listOfFiles = properties.getFiles();
    	CustomerController getCustomers = new CustomerController();
    	getCustomers.setFileLocation(location);
    	getCustomers.setFiles(listOfFiles);
    	SpringApplication.run(CustomerApplication.class, args);
    }
}
