package com.git.felipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebCardenetaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCardenetaApiApplication.class, args);
	}
	
}
