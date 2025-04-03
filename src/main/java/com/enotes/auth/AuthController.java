package com.enotes.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.user.UserRequest;
import com.enotes.user.AuthService;
import com.enotes.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;
		
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto,HttpServletRequest req) throws Exception{
		String url = CommonUtil.getUrl(req);
		Boolean register = authService.register(userDto,url);
		if(register) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED,"user registered successfully");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "registeration failed");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUserHandler(@RequestBody LoginRequest req){
		LoginResponse loginUser = authService.loginUser(req);
		if(ObjectUtils.isEmpty(loginUser)) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.BAD_REQUEST, "invalid credentials");
		}
		return CommonUtil.createBuildResponse(loginUser, HttpStatus.OK);
	}
}








