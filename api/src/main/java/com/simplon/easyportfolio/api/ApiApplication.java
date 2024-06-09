package com.simplon.easyportfolio.api;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApiApplication extends SpringBootServletInitializer implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	public void run(ApplicationArguments args) throws Exception {
		System.out.println("L'api a démarré avec succès!");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ApiApplication.class);
	}

}
