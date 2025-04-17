package com.enotes.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.enotes.auth.RoleRepository;
import com.enotes.category.CategoryDTO;
import com.enotes.exception.DataExistsException;
import com.enotes.user.UserRequest;
import com.enotes.user.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class Validation extends TodoValidation{

	private final RoleRepository roleRepo;
	
	private final UserRepository userRepo;
	
	public void categoryValidation(CategoryDTO categoryDto) {

		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category object should not be blank or empty");
		} else {

			// Validation for name field.
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name cannot be empty or null");
			} else {
				if (categoryDto.getName().length() < 3) {
					error.put("name", "name min length is 3 ");
				}
				if (categoryDto.getName().length() > 100) {
					error.put("name", "name max length is 10 ");
				}
			}

			// Validation for description field.

			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description cannot be empty or null");
			}

			
			// Validation for isActive field.
            
			if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				System.err.print(categoryDto.getIsActive());
				error.put("isActive", "name cannot be empty or null");
			} else {
				if (categoryDto.getIsActive() == null || (!(categoryDto.getIsActive() instanceof Boolean))) {
					error.put("isActive", "isActive must be a boolean value (true or false)");
				}
			}
		}
		if (!error.isEmpty()) {
			throw new com.enotes.exception.ValidationException(error);
		}
	}
	
	// Validation for user request
	
	public void userValidation(UserRequest user) throws BadRequestException {
		if(!StringUtils.hasText(user.getFirstName())) {
			throw new BadRequestException("first name is invalid ");
		}
		
		if(!StringUtils.hasText(user.getLastName())) {
			throw new BadRequestException("last name is invalid ");
		}
		
		if(!StringUtils.hasText(user.getEmail()) ||  !user.getEmail().matches(Constants.EMAIL_REGEX)) {
			throw new BadRequestException("email is invalid ");
		}
		else {
			Boolean isExists=userRepo.existsByEmail(user.getEmail());
			if(isExists) {
				log.error("Validation : categoryValidation() : Email already exists.");
				throw new DataExistsException("email allready exists");
			}
		}
		if(!StringUtils.hasText(user.getMobileNo().trim()) ||  !user.getMobileNo().trim().matches(Constants.MOBNO_REGEX)) {
			throw new BadRequestException("mobile number is invalid ");
		}
		
		if (CollectionUtils.isEmpty(user.getRoles())) {
			throw new IllegalArgumentException("role is invalid");
		} else {

			List<Integer> roleIds = roleRepo.findAll().stream().map(r -> r.getId()).toList();

			List<Integer> invalidReqRoleids = user.getRoles().stream().map(r -> r.getId())
					.filter(roleId -> !roleIds.contains(roleId)).toList();

			if (!CollectionUtils.isEmpty(invalidReqRoleids)) {
				throw new IllegalArgumentException("role is invalid" + invalidReqRoleids);
			}

		}

	}
}
