package com.enotes.user;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.utils.CommonUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

	private final ModelMapper mapper;
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(){
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
		
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
	
	@PostMapping("/change-pswd")
	public ResponseEntity<?> changePasswordHandler(@RequestBody PasswordChangeRequest req){
		userService.changePassword(req);
		return CommonUtil.createErrorResponseMessage(HttpStatus.OK,"password change successfully ");
	}
}
