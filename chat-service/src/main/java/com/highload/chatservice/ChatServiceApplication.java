package com.highload.chatservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
@EnableR2dbcRepositories
public class ChatServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatServiceApplication.class, args);
	}

}
