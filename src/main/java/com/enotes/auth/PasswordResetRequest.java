/**
 * @author Vaibhav Borkar
 * @explanation This class is responsible for defining the password reset parameter means how many paremteter will be accepted for the password reset request.
 */

package com.enotes.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetRequest {

	private Integer uid;
	
	private String newPassword;
}
