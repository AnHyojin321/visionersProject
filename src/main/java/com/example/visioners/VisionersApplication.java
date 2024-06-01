package com.example.visioners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.visioners.repository")
@EntityScan(basePackages = "com.example.visioners.dto")
public class VisionersApplication {
	public static void main(String[] args) {
		SpringApplication.run(VisionersApplication.class, args);
	}
}
