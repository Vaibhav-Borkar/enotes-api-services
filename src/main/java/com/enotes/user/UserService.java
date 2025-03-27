package com.enotes.user;

import com.enotes.auth.LoginRequest;
import com.enotes.auth.LoginResponse;

public interface UserService {

	public Boolean register(UserRequest user, String url) throws Exception;

	public LoginResponse loginUser(LoginRequest req);
}
