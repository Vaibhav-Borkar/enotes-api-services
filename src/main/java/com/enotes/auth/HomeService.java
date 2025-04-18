/**
 * @author Vaibhav Borkar
 * @explanation This class contains the service logic for HomeController 
 */
package com.enotes.auth;

public interface HomeService {

	public Boolean verifyAccount(Integer userId,String verificationCode);
}
