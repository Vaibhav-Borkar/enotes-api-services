package com.enotes.user;

import com.enotes.auth.LoginRequest;
import com.enotes.auth.LoginResponse;

/**
 * @author Vaibhav Borkar
 * 
 * @explanation : It contains the user related task like register and login so instead of UserService we can give the name as AuthService for better readability so in future we can decide this class meant for the performing the tasks before the login.
 *  
 */

public interface AuthService {

	public Boolean register(UserRequest user, String url) throws Exception;

	public LoginResponse loginUser(LoginRequest req);
}
