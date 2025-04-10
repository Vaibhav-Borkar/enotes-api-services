package com.enotes.user;

import com.enotes.auth.PasswordResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	void changePassword(PasswordChangeRequest req);

	void verifyPasswordResetLink(Integer uid, String code);

	void sendEmailForPasswordReset(String email,HttpServletRequest req) throws Exception;

	void resetPassword(PasswordResetRequest req,HttpServletRequest request);
}
