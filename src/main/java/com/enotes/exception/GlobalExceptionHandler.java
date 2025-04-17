package com.enotes.exception;

/**
 * @CONCLUSION IF WE NOT HANDLE THE EXCEPTION EXPLICITLY IN CONTROLLER OR OTHER CLASSES
 * THEN ONLY THE GLOBAL EXCEPTION WORKS OTHERWISE NOT.
 * IF YOU HANDLE THE EXCEPTION USING TRY CATCH THEN IN THIS CASE IT WONT WORKS.
 * 
 * @IMPORTANT IT CANT HANDLE THE FILTER EXCEPTION LIKE IF THE JWT SIGNATURE VERIFICATION FAILED THEN IN THIS CASE THIS EXCEPTION CANT REACH THE GLOBAL EXCEPTION HANDLER.
 */
import java.io.FileNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;	
import com.enotes.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice 
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception e) {
		log.info("GlobalExceptionHandler : handleGlobalException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e){
		log.info("GlobalExceptionHandler : handleNullPointerException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e){
		log.info("GlobalExceptionHandler : handleResourceNotFoundException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e){
		log.info("GlobalExceptionHandler : handleValidationException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponse( e.getErrors(),HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(DataExistsException.class)
	public ResponseEntity<?> handleDataExistsException(DataExistsException e){
		log.info("GlobalExceptionHandler : handleDataExistsException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponseMessage(HttpStatus.CONFLICT, e.getMessage());
	}
	
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException e){
		log.info("GlobalExceptionHandler : handleCategoryNotFoundException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(UnSupportedFileException.class)
	public ResponseEntity<?> handleUnSupportedFileException(UnSupportedFileException e){
		log.info("GlobalExceptionHandler : handleUnSupportedFileException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponseMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e){
		log.info("GlobalExceptionHandler : handleFileNotFoundException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> handleBadRequestException(BadRequestException e){
		log.info("GlobalExceptionHandler : handleBadRequestException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(SuccessException e){
		log.info("GlobalExceptionHandler : handleSuccessException() : {}",e.getMessage());
		return  CommonUtil.createBuildResponseMessage(HttpStatus.OK, e.getMessage());
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e){
		log.info("GlobalExceptionHandler : handleBadCredentialsException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException e){
		log.info("GlobalExceptionHandler : handleAuthorizationDeniedException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponse(e.getMessage(),HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e){
		log.info("GlobalExceptionHandler : handleAccessDeniedException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponse(e.getMessage(),HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<?> handlePasswordMismatchException(PasswordMismatchException e){
		log.info("GlobalExceptionHandler : handlePasswordMismatchException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponse(e.getMessage(),HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e){
		log.info("GlobalExceptionHandler : handleUserNotFoundException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
		log.info("GlobalExceptionHandler : handleIllegalArgumentException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PasswordException.class)
	public ResponseEntity<?> handlePasswordException(PasswordException e){
		log.info("GlobalExceptionHandler : handlePasswordException() : {}",e.getMessage());
		return  CommonUtil.createErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
