package com.enotes.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.utils.SWAGGER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todo APIs", description = "This controller contains the APIs related to Todo module")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

	@Operation(summary = "Get Profile", description = SWAGGER.GET_PROFILE)
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();

	@Operation(summary = "Change Password", description = SWAGGER.CHANGE_PASSWORD)
	@PostMapping("/change-pswd")
	public ResponseEntity<?> changePasswordHandler(@RequestBody PasswordChangeRequest req);

}
