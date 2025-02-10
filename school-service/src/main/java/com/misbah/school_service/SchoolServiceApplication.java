package com.misbah.school_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SchoolServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolServiceApplication.class, args);
	}

}
