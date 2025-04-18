/**
 * @author Vaibhav Borkar
 * @explanation This class responsible for handling user verification,forget password ,reset password like operations.This class also implements HomeEndpoint where all endpoints declared because of security reasons.
 */

package com.enotes.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.enotes.user.UserService;
import com.enotes.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class HomeController implements HomeEndpoint {
	
	private final HomeService homeService;
	private final UserService userService;
	
	@Override
	public ResponseEntity<?> verifyAccountHandler( Integer uid , String code){
		log.info("HomeController : verifyAccountHandler() : Execution start");
		Boolean isVerified = homeService.verifyAccount(uid, code);
		if(isVerified) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK,"account verified successfully");
		}
		log.info("HomeController : verifyAccountHandler() : Execution end");
		return CommonUtil.createErrorResponseMessage(HttpStatus.BAD_REQUEST	,"invalid verification link");	
	}

	@Override
	public ResponseEntity<?> sendEmailForPasswordResetHandler( String email,HttpServletRequest  req) throws Exception{
		userService.sendEmailForPasswordReset(email,req);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "email sent successfully ! please check email box for verification.");
	}
	
	@Override
	public ResponseEntity<?> verifyPasswordResetLinkHandler( Integer uid, String code){
		userService.verifyPasswordResetLink(uid,code);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "email verification success.");
		
	}
	
	@Override
	public ResponseEntity<?> resetPasswordHandler( PasswordResetRequest req,HttpServletRequest httpRequest){
		userService.resetPassword(req,httpRequest);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "password reset successfully.");
	}
}
