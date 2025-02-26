package com.enotes.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enotes.GenericResponse;

public class CommonUtil {

	public static ResponseEntity<?> createBuildResponse(Object data,HttpStatus status){
		GenericResponse response=GenericResponse.builder()
				.responseStatus(status)
				.status("success")
				.message("success")
				.data(data)
				.build();
		return response.create();
	}
	
	public static ResponseEntity<?> createBuildResponseMessage(HttpStatus status,String message){
		GenericResponse response=GenericResponse.builder()
				.responseStatus(status)
				.status("success")
				.message(message)
				.build();
		return response.create();
	}
	
	
	public static ResponseEntity<?> createErrorResponse(Object data,HttpStatus status){
		GenericResponse response=GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message("failed")
				.data(data)
				.build();
		return response.create();
	}
	
	
	public static ResponseEntity<?> createErrorResponseMessage(HttpStatus status,String message){
		GenericResponse response=GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message(message)
				.build();
		return response.create();
	}
	
}
