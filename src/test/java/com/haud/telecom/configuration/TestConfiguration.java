package com.haud.telecom.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.haud.telecom.service.CustomerService;
import com.haud.telecom.service.SimCardService;

@Profile("test")
@Configuration
public class TestConfiguration {

	@Bean
	@Primary
	public CustomerService customerService() {
		return Mockito.mock(CustomerService.class);
	}
	
	@Bean
	@Primary
	public SimCardService simCardService() {
		return Mockito.mock(SimCardService.class);
	}
}
