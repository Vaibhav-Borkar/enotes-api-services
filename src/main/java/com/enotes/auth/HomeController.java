package com.enotes.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.user.UserService;
import com.enotes.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/home")
@AllArgsConstructor
public class HomeController {
	
	private final HomeService homeService;
	private final UserService userService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccountHandler(@RequestParam Integer uid ,@RequestParam String code){
		log.info("HomeController : verifyAccountHandler() : Execution start");
		Boolean isVerified = homeService.verifyAccount(uid, code);
		if(isVerified) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK,"account verified successfully");
		}
		log.info("HomeController : verifyAccountHandler() : Execution end");
		return CommonUtil.createErrorResponseMessage(HttpStatus.BAD_REQUEST	,"invalid verification link");	
	}

	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordResetHandler(@RequestParam String email,HttpServletRequest  req) throws Exception{
		userService.sendEmailForPasswordReset(email,req);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "email sent successfully ! please check email box for verification.");
	}
	
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLinkHandler(@RequestParam Integer uid,@RequestParam String code){
		userService.verifyPasswordResetLink(uid,code);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "email verification success.");
		
	}
	
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPasswordHandler(@RequestBody PasswordResetRequest req,HttpServletRequest httpRequest){
		userService.resetPassword(req,httpRequest);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "password reset successfully.");
	}
}
