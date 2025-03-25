package com.enotes.user;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.enotes.config.EmailRequest;
import com.enotes.config.EmailService;
import com.enotes.utils.Validation;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final Validation validation;
	private final ModelMapper mapper;
	private final EmailService emailService;

	@Override
	public Boolean register(UserDTO userDto,String url) throws Exception {
		validation.userValidation(userDto);
		User user = mapper.map(userDto, User.class);
		setRole(userDto, user);
		AccountStatus accountStatus = AccountStatus.builder()
		.isActive(false)
		.verificationCode(UUID.randomUUID().toString())
		.build();
		user.setStatus(accountStatus);
		User save = userRepo.save(user);
		if (!ObjectUtils.isEmpty(save)) {
			emailSend(save,url);
			return true;
		}
		return false;
	}

	private void emailSend(User user,String url) throws Exception {
		String message="Hii, <br>[[username]]<br>"
				+ "<br> Your account register successfully .<br>"
				+ "<br> Click the bellow link for verify & active you account <br>"
				+ "<a href='[[url]]'> Click Here <a> <br> <br>"
				+ "Thanks ! <br> Enotes.com";
		message = message.replace("[[username]]", user.getFirstName());
		message=message.replace("[[url]]", url+"/api/v1/home/verify?uid="+user.getId()+"&&code="+user.getStatus().getVerificationCode());
		
		EmailRequest request = EmailRequest.builder()
				.to(user.getEmail())
				.title("Account Creation Confirmation")
				.subject("Account created success")
				.message(message)
				.build();
		emailService.sendEmail(request);
	}

//	private void setRole(UserDTO userDto, User user) {
//		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
//		List<Role> roles = roleRepo.findAllById(reqRoleId);
//		user.setRoles(roles);
//	}
	private void setRole(UserDTO userDto, User user) {
		List<Integer> reqRoleIds = userDto.getRoles().stream().map(UserDTO.RoleDTO::getId).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleIds);
		user.getRoles().clear(); // Ensure roles are updated properly
		user.getRoles().addAll(roles);
	}
}
