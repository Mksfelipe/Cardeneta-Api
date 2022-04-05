package com.git.felipe;

import java.util.concurrent.TimeUnit;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

import com.github.benmanes.caffeine.cache.Caffeine;

@SpringBootApplication
@EnableCaching
public class WebCardenetaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCardenetaApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public Caffeine<Object, Object> caffeineConfig() {
		return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
	}
	
	@Bean
	public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		cacheManager.setCaffeine(caffeine);
		return cacheManager;
	}
}
