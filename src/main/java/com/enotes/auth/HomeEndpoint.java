/**
 * @author Vaibhav Borkar
 * @explanation This class provide the abstraction for the HomeController end-points so our end-points can not expose directly .
 *              We define the end-points here and the controller class provide the implements for this methods.
 */

package com.enotes.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccountHandler(@RequestParam Integer uid ,@RequestParam String code);	
	
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordResetHandler(@RequestParam String email,HttpServletRequest  req) throws Exception;
	
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLinkHandler(@RequestParam Integer uid,@RequestParam String code);
	
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPasswordHandler(@RequestBody PasswordResetRequest req,HttpServletRequest httpRequest);
	
}
