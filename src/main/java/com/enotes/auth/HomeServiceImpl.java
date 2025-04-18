/**
 * @author Vaibhav Borkar
 * @explanation This class contains the service logic implementation for HomeController 
 */

package com.enotes.auth;

import org.springframework.stereotype.Service;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.SuccessException;
import com.enotes.user.User;
import com.enotes.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {

	private final UserRepository userRepo;
	
	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) {
		log.info("HomeServiceImpl : verifyAccount() : Start");
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("invalid user"));
		
		if(user.getStatus().getVerificationCode()==null) {
			log.info("Message : account already verified");
			throw new SuccessException("account already verified");
		}
		
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			userRepo.save(user);
			log.info("Message : account verified successfully");
			return true;
		}
		log.info("HomeServiceImpl : verifyAccount() : End");
		return false;
	}

}
