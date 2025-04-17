package com.enotes.user;

import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.enotes.auth.AccountStatus;
import com.enotes.auth.LoginRequest;
import com.enotes.auth.LoginResponse;
import com.enotes.auth.Role;
import com.enotes.auth.RoleRepository;
import com.enotes.config.EmailRequest;
import com.enotes.config.EmailService;
import com.enotes.security.CustomUserDetails;
import com.enotes.security.JwtService;
import com.enotes.utils.Validation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Vaibhav Borkar
 * @explanation This class provide the implementation for AuthService interface.
 */

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final Validation validation;
	private final ModelMapper mapper;
	private final EmailService emailService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Override
	public Boolean register(UserRequest userDto, String url) throws Exception {
        log.info("AuthServiceImpl : register() : Start");
		validation.userValidation(userDto);
		User user = mapper.map(userDto, User.class);
		setRole(userDto, user);
		AccountStatus accountStatus = AccountStatus.builder().isActive(false)
				.verificationCode(UUID.randomUUID().toString()).build();

		user.setStatus(accountStatus);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.info("AuthServiceImpl : register() : Register user saved in DB successfully");
		User save = userRepo.save(user);
		if (ObjectUtils.isEmpty(save)) {
			return false;
		}
		log.info("AuthServiceImpl : register() : emailSendForRegister() called ");
		emailSendForRegister(save, url);
		log.info("AuthServiceImpl : register() : End");
		return true;
		
	}

	private void emailSendForRegister(User user, String url) throws Exception {
		log.info("AuthServiceImpl : emailSendForRegister() : Start");
		String message = "<html><body>"
				+ "<div style='font-family: Arial, Helvetica, sans-serif; background-color: #ffffff; padding: 20px;'>"
				+ "<div style='max-width: 600px; margin: auto; background-color: #f4f6f9; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); text-align: center;'>"
				+ "<div style='margin-bottom: 20px;'><img src='https://cdn-icons-png.flaticon.com/512/742/742751.png' width='70px'></div>"
				+ "<h2 style='font-size: 24px; color: #2C3E50;'>Please verify your email 😊</h2>"
				+ "<p style='font-size: 16px; color: #34495E;'>To use Enotes, click the verification button below. This helps keep your account secure.</p>"
				+ "<p style='text-align: center;'>"
				+ "<a href='[[url]]' style='background-color: #007bff; color: #ffffff; padding: 12px 25px; text-decoration: none; font-size: 16px; border-radius: 5px; display: inline-block; margin-top: 20px;'>Verify my account</a>"
				+ "</p>"
				+ "<p style='font-size: 16px; color: #34495E;'>You're receiving this email because you have an account in Enotes.</p>"
				+ "<p style='font-size: 14px; color: #7F8C8D;'>If you are not sure why you're receiving this, please contact us by replying to this email.</p>"
				+ "<div style='background-color: #ecf0f1; padding: 10px; border-radius: 5px; margin-top: 20px;'>"
				+ "<p style='font-size: 14px; color: #7F8C8D;'>Email specialists use Enotes' intuitive tools to design emails for desktop and mobile.</p>"
				+ "</div>" + "</div>" + "</div>" + "</body></html>";

		log.info("AuthServiceImpl : emailSendForRegister() : Url build successfully");
		message = message.replace("[[url]]",
				url + "/api/v1/home/verify?uid=" + user.getId() + "&&code=" + user.getStatus().getVerificationCode());

		log.info("AuthServiceImpl : emailSendForRegister() : Subject message title set successfully");
		EmailRequest request = EmailRequest.builder().to(user.getEmail()).title("Email Verification")
				.subject("Please Verify Your Email at Enotes").message(message).build();

		log.info("AuthServiceImpl : emailSendForRegister() : sendEmail() called ");
		emailService.sendEmail(request);
		log.info("AuthServiceImpl : emailSendForRegister() : End");
	}

	private void setRole(UserRequest userDto, User user) {
		List<Integer> reqRoleIds = userDto.getRoles().stream().map(UserRequest.RoleDTO::getId).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleIds);
		user.getRoles().clear(); // Ensure roles are updated properly
		user.getRoles().addAll(roles);
	}

	@Override
	public LoginResponse loginUser(LoginRequest req) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
		if (authenticate.isAuthenticated()) {
			CustomUserDetails details = (CustomUserDetails) authenticate.getPrincipal();
			String token = jwtService.generateToken(details.getUser());

			LoginResponse loginResponse = LoginResponse.builder().token(token)
					.user(mapper.map(details.getUser(), UserResponse	.class)).build();
			return loginResponse;
		}
		return null;
	}
}
