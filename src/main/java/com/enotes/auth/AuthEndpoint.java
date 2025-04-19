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
import com.enotes.utils.SWAGGER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Authentication APIs",description = "All users authentication APIs like register and login")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

	@Operation(summary = "Register User",description = SWAGGER.REGISTER_USER)
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto,HttpServletRequest req) throws Exception;
	
	@Operation(summary = "Login User",description = SWAGGER.LOGIN_USER)
	@PostMapping("/login")
	public ResponseEntity<?> loginUserHandler(@RequestBody LoginRequest req);
}
