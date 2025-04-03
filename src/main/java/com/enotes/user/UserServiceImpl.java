package com.enotes.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.enotes.exception.PasswordMismatchException;
import com.enotes.utils.CommonUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepo;
	
	@Override
	public void changePassword(PasswordChangeRequest req) {
		User loggedInUser = CommonUtil.getLoggedInUser();
		if(! passwordEncoder.matches(req.getOldPassword(),loggedInUser.getPassword())) {
			 throw new PasswordMismatchException("old password is incorrect !");
		}
		loggedInUser.setPassword(passwordEncoder.encode(req.getNewPassword()));
		userRepo.save(loggedInUser);
	
	}

}
