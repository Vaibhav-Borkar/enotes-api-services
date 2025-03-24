package com.enotes.user;

import java.util.List;
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
public class UserDTO {

	private Integer id;

	private String firstName;

	private String lastName;

	private String email;

	private String mobileNo;
	
	private List<RoleDTO> roles;
	
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class RoleDTO{
		private Integer id;
		
		private String name;
	}
}
