package com.enotes.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ProjectConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public AuditorAware<Integer> auditAware(){
		return new AuditAwareConfig();
	}
	
//	@Bean
//	public ObjectMapper objectMapper() {
//		return new ObjectMapper();
//	}
	
	@Bean
	public ObjectMapper objectMapper() {
	    return new ObjectMapper()
	            .registerModule(new JavaTimeModule())  // Support for LocalDateTime
	            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Use ISO-8601 format
	}

	
	
}
