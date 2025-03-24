package com.enotes.user;

import org.apache.coyote.BadRequestException;

public interface UserService {

	public Boolean register(UserDTO user) throws BadRequestException;
}
