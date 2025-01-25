package com.cosmo.my_auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MyAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyAuthServiceApplication.class, args);
	}

}
