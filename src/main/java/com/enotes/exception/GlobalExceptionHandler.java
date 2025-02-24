package com.enotes.exception;

/*
 * IF WE NOT HANDLE THE EXCEPTION EXPLICITLY IN CONTROLLER OR OTHER CLASSES
 * THEN ONLY THE GLOBAL EXCEPTION WORKS OTHERWISE NOT.
 * IF YOU HANDLE THE EXCEPTION USING TRY CATCH THEN IN THIS CASE IT WONT WORKS.
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice 
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception e) {
		log.info("GlobalExceptionHandler :: handleGlobalException");
		return new ResponseEntity<> (e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	
//	@ExceptionHandler(InternalServerError.class)
//	public ResponseEntity<?> handleInternalServerError(Exception e){
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
}
