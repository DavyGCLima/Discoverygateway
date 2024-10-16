package com.example.eureka_gateway.eureka_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EurekaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaGatewayApplication.class, args);
	}

}
