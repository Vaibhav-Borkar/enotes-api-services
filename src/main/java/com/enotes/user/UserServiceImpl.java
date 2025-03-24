package com.enotes.user;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.utils.Validation;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final Validation validation; 
	private final ModelMapper mapper;
	
	@Override
	public Boolean register(UserDTO userDto) throws BadRequestException {
		validation.userValidation(userDto);
		User user = mapper.map(userDto, User.class);
		setRole(userDto, user);
		User save = userRepo.save(user);
		if(!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;
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
