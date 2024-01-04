package com.glez.mng_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.glez.*")
@ComponentScan(basePackages = {"com.glez.*"})
@EntityScan("com.glez.*")
public class MngServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MngServiceApplication.class, args);
	}

}
