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

import com.enotes.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice 
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception e) {
		log.info("GlobalExceptionHandler :: GlobalExceptionHandler");
//		return new ResponseEntity<> (e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return  CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e){
		log.info("NullPointerException :: NullPointerException");
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return  CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e){
		log.info("ResourceNotFoundException :: ResourceNotFoundException");
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		return  CommonUtil.createErrorResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e){
		log.info("ValidationException :: ValidationException");
//		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
		return  CommonUtil.createErrorResponse( e.getErrors(),HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(DataExistsException.class)
	public ResponseEntity<?> handleDataExistsException(DataExistsException e){
		log.info("DataExistsException :: DataExistsException");
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		return  CommonUtil.createErrorResponseMessage(HttpStatus.CONFLICT, e.getMessage());
	}
	
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException e){
		log.info("CategoryNotFoundException :: CategoryNotFoundException");
		return  CommonUtil.createErrorResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(UnSupportedFileException.class)
	public ResponseEntity<?> handleUnSupportedFileException(UnSupportedFileException e){
		log.info("UnSupportedFileException :: UnSupportedFileException");
		return  CommonUtil.createErrorResponseMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
	}
	
}
