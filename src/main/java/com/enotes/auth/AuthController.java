/**
 * @author Vaibhav Borkar
 * @explanation This class contains the login and register api logic 
 */

package com.enotes.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import com.enotes.user.UserRequest;
import com.enotes.user.AuthService;
import com.enotes.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController implements AuthEndpoint {

	private final AuthService authService;
		
	@Override
	public ResponseEntity<?> registerUser(UserRequest userDto,HttpServletRequest req) throws Exception{
		log.info("AuthController : registerUser() : Start");
		String url = CommonUtil.getUrl(req);
		Boolean register = authService.register(userDto,url);
		if(!register) {
			log.error("AuthController : registerUser() : User registration failed .");
			return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "registeration failed");
		}
		log.info("AuthController : registerUser() : End");
		return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED,"user registered successfully");
	}
	
	@Override
	public ResponseEntity<?> loginUserHandler( LoginRequest req){
		LoginResponse loginUser = authService.loginUser(req);
		if(ObjectUtils.isEmpty(loginUser)) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.BAD_REQUEST, "invalid credentials");
		}
		return CommonUtil.createBuildResponse(loginUser, HttpStatus.OK);
	}
}








