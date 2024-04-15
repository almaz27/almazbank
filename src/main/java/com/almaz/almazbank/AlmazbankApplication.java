package com.almaz.almazbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
@EnableScheduling
public class AlmazbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlmazbankApplication.class, args);
	}

}
