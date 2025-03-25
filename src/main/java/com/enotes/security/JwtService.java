package com.enotes.security;

import com.enotes.user.User;

public interface JwtService {

	public String generateToken(User user);
	
}
