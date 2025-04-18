package com.enotes.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user")
public interface UserEndpoint {

	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();

	@PostMapping("/change-pswd")
	public ResponseEntity<?> changePasswordHandler(@RequestBody PasswordChangeRequest req);

}
