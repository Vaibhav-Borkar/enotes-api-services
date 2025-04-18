/**
 * @author Vaibhav Borkar
 * @explanation This class responsible for handling the user account status.Like user account status is active or not user email verification code and many more things.
 */

package com.enotes.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AccountStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Boolean isActive;
	
	private String verificationCode;
	
	private String passwordResetToken;
	
	private String ipAddress;
}
