/**
 * @author Vaibahv Borkar
 * @explanation This class contains the login request formate entity .
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
public class LoginRequest {

	private String email;
	
	private String password;
}
