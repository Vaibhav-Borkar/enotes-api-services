/**
 * @author Vaibhav Borkar
 * @explanation Most important class for managing the user roles and there access in the application.
 */

package com.enotes.auth;

import java.util.ArrayList;
import java.util.List;

import com.enotes.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@ManyToMany(mappedBy = "roles")
	@JsonBackReference
	private List<User> users = new ArrayList<>();
}
