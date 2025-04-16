package com.enotes.user;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.enotes.auth.PasswordResetRequest;
import com.enotes.config.EmailRequest;
import com.enotes.config.EmailService;
import com.enotes.exception.PasswordException;
import com.enotes.exception.PasswordMismatchException;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.UserNotFoundException;
import com.enotes.utils.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepo;
	private EmailService emailService;

	@Override
	public void changePassword(PasswordChangeRequest req) {
		log.info("New password in request :{}",req.getNewPassword());
		log.info("Old password in request :{}",req.getOldPassword());
		User loggedInUser = CommonUtil.getLoggedInUser();
		if (!passwordEncoder.matches(req.getOldPassword(), loggedInUser.getPassword())) {
			throw new PasswordMismatchException("old password is incorrect !");
		}
		loggedInUser.setPassword(passwordEncoder.encode(req.getNewPassword()));
		userRepo.save(loggedInUser);
	}

	@Override
	public void sendEmailForPasswordReset(String email, HttpServletRequest req) throws Exception {
//		 We are getting HttpServletRequest for getting the dynamic port and url from the controller instead of using single static port and url if the url is change the it can also perform as well.
		log.info("User email for reseting password :{}",email);
		User user = userRepo.findByEmail(email);
                            
		if (ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("invalid email");
		}
		// Generate unique password reset token .
		String passwordResetToken = UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(passwordResetToken);
		User updatedUser = userRepo.save(user);
		String url = CommonUtil.getUrl(req);
		sendEmailForPasswordReset(updatedUser, url);
		
	}

	private void sendEmailForPasswordReset(User user, String url) throws UnsupportedEncodingException, MessagingException {
		log.info("Sending email reset for user :{}",user.getId());
		log.info("Url for getting dynamic host port and url :{}",url);
		String message = "Hii <b>[[username]]</b>" + "<br><p>You requested to reset the password </p><br>"
				+ "<p> click the below link to change the password </p>"
				+ "<p> <a href=[[url]]> Change my password</a></p>";

		message = message.replace("[[username]]", user.getFirstName());
		message = message.replace("[[url]]", url + "/api/v1/home/verify-pswd-link?uid=" + user.getId() + "&&code="
				+ user.getStatus().getPasswordResetToken());

		EmailRequest request = EmailRequest.builder()
				.to(user.getEmail())
				.title("Password Reset")
				.subject("Password Reset Link")
				.message(message).build();

		emailService.sendEmail(request);
	}

	@Override
	public void verifyPasswordResetLink(Integer uid, String code) {
		User user = userRepo.findById(uid).orElseThrow(()-> new UserNotFoundException("user not found for this id"));
		log.info("Verification code for user: {}", user.getStatus().getPasswordResetToken());
		verifyPasswordResetToken(user.getStatus().getPasswordResetToken(),code);
		
	}

	private boolean verifyPasswordResetToken(String existingToken, String reqToken) {
		log.info("Existing token is :{}",existingToken);
		log.info("Request token is :{}",reqToken);
		// Checking request token should not be null.
		if(StringUtils.hasText(reqToken)) {
			
			// After reseting the password if user re-click the same link. 
			if(!StringUtils.hasText(existingToken)) {
				throw new IllegalArgumentException("password has already been reset!");

			}
			
			// Existing token and request token is not matched.
			if(!existingToken.equals(reqToken)) {
				throw new IllegalArgumentException("invalid reset url");
			}
			return true;
		}
		else {
			throw new IllegalArgumentException("invalid reset token !");
		}
		
	}

	@Override
	public void resetPassword(PasswordResetRequest req,HttpServletRequest httpRequest) {
		User user = userRepo.findById(req.getUid()).orElseThrow(()-> new UserNotFoundException("user not found for this id"));
		// Use full when the user already reseted the password and trying to use same url without sending the other email verification mail.
		if(user.getStatus().getPasswordResetToken()==null) {
			log.info("Password reset token : {}",user.getStatus().getPasswordResetToken());
			throw new PasswordException("email verification failed please resend the password reset link");
		}
		String encodedPassword=passwordEncoder.encode(req.getNewPassword());
		user.setPassword(encodedPassword);
		user.getStatus().setPasswordResetToken(null);
		// Save the user ipaddress for information means who change the password.
		user.getStatus().setIpAddress(httpRequest.getRemoteAddr());
		userRepo.save(user);
		log.info("Password reset successfully for user ID: {}", user.getId());

	}

}
