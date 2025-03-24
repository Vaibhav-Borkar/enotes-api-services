package com.enotes.user;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.utils.CommonUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class AuthController {

	private final UserService userService;
	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDto) throws BadRequestException{
		Boolean register = userService.register(userDto);
		if(register) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED,"user registered successfully");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "registeration failed");
	}
}
