package com.enotes.security;

import org.springframework.security.core.userdetails.UserDetails;

import com.enotes.user.User;

public interface JwtService {

	public String generateToken(User user);
	
	public String extractUsername(String jwtToken);
	
	public Boolean validateToken(String jwtToken,UserDetails details);
	
}
