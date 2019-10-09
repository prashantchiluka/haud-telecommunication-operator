package com.haud.api.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.haud.api.impl.CustomerService;
import com.haud.api.impl.SimCardService;

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
