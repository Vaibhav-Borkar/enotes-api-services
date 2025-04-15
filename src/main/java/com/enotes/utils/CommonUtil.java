package com.enotes.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import com.enotes.GenericResponse;
import com.enotes.security.CustomUserDetails;
import com.enotes.user.User;
import jakarta.servlet.http.HttpServletRequest;

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

	public static String getUrl(HttpServletRequest req) {
		String apiUrl = req.getRequestURL().toString(); // http://localhost:8080/api/v1/auth
		apiUrl= apiUrl.replace(req.getServletPath(), "");
		return apiUrl; // http://localhost:8080
	}

	public static User getLoggedInUser() {
		CustomUserDetails loggedUser=(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return loggedUser.getUser();
	}
	
}
