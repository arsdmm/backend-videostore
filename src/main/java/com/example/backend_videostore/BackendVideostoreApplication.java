package com.example.backend_videostore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
  Entry point for the Spring Boot application.

  @SpringBootApplication is a convenience annotation that combines:
  - @Configuration: allows registration of beans
  - @EnableAutoConfiguration: enables Spring Boot’s auto-configuration mechanism
  - @ComponentScan: scans the package for components, configurations, and services
*/
@SpringBootApplication
public class BackendVideostoreApplication {

	/*
	  The main() method is the starting point of the application.
	  It bootstraps the Spring context and starts the embedded server (e.g., Tomcat).
	*/
	public static void main(String[] args) {
		SpringApplication.run(BackendVideostoreApplication.class, args);
	}
}
