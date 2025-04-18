/**
 * @author Vaibhav Borkar
 * @explanation This class work as a data transfer object means how much data will be sent to the use after login.
 */

package com.enotes.auth;

import com.enotes.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {

	private UserResponse user;
	
	private String token;
	
}
