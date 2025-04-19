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

import com.enotes.utils.SWAGGER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
@Tag(name = "Home APIs",description = "This controller contains the APIs for verification,sending mail for reset and may more")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

	@Operation(summary = "Verify Account",description = SWAGGER.VERIFY_ACCOUNT)
	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccountHandler(@RequestParam Integer uid ,@RequestParam String code);	
	
	@Operation(summary = " Sending Mail for Password Reset",description = SWAGGER.SEND_EMAIL_RESET)
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordResetHandler(@RequestParam String email,HttpServletRequest  req) throws Exception;
	
	@Operation(summary="Verify Password Link",description = SWAGGER.VERIFY_PASSWORD_LINK)
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLinkHandler(@RequestParam Integer uid,@RequestParam String code);
	
	@Operation(summary = "Reset Password",description = SWAGGER.RESET_PASSWORD)
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPasswordHandler(@RequestBody PasswordResetRequest req,HttpServletRequest httpRequest);
	
}
