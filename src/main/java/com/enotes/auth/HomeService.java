package com.enotes.auth;

public interface HomeService {

	public Boolean verifyAccount(Integer userId,String verificationCode);
}
