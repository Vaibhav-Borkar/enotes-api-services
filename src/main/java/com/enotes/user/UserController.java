package com.enotes.user;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.enotes.utils.CommonUtil;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController implements UserEndpoint {

	private final ModelMapper mapper;
	private UserService userService;

	@Override
	public ResponseEntity<?> getProfile() {
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);

		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> changePasswordHandler( PasswordChangeRequest req) {
		userService.changePassword(req);
		return CommonUtil.createErrorResponseMessage(HttpStatus.OK, "password change successfully ");
	}
}
