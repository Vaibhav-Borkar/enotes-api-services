package com.enotes.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.enotes.utils.FileDetailsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	
}
