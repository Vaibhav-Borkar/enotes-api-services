package com.enotes.user;

import org.springframework.stereotype.Service;

import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.SuccessException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {

	private final UserRepository userRepo;
	
	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("invalid user"));
		
		if(user.getStatus().getVerificationCode()==null) {
			throw new SuccessException("account already verified");
		}
		
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			userRepo.save(user);
			return true;
		}
		return false;
	}

}
