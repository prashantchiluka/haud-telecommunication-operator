package com.haud.telecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan(basePackages = "com.haud.telecom.entity")
@EnableJpaRepositories(basePackages = "com.haud.telecom.repository")
public class HaudTelecommunicationOperatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaudTelecommunicationOperatorApplication.class, args);
	}
}
