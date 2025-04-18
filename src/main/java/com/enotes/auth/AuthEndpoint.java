/**
 * @author Vaibhav Borkar
 * @explanation This class provide the abstraction for the AuthController end-points, so our end-points can not expose directly .
 *              We define the end-points here and the controller class provide the implements for this methods.
 */

package com.enotes.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.user.UserRequest;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto,HttpServletRequest req) throws Exception;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUserHandler(@RequestBody LoginRequest req);
}
