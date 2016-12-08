package com.gl.customer;

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
public class CustomerApplication 
{
    public static void main( String[] args )
    {
    	String customerProp = args[0];
    	String log4j2Prop = args[1];
    	GetCustomerProperties properties = new GetCustomerProperties();
    	try {
			properties.getProperties(customerProp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String location = properties.location();
    	File[] listOfFiles = properties.getFiles();
    	CustomerController getCustomers = new CustomerController();
    	getCustomers.setFileLocation(location);
    	getCustomers.setFiles(listOfFiles);
    	getCustomers.setLogProperties(log4j2Prop);
    	SpringApplication.run(CustomerApplication.class, args);
    }
}
