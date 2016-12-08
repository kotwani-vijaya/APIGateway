package de.michlb.demo.zuul;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Application {
  public static void main(String[] args) {
	  String log4j2Path = args[0];
	  PropertyConfigurator.configure(log4j2Path);
	  SpringApplication.run(Application.class, args);
  }
}
