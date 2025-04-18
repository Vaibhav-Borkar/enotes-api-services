/**
 * @author Vaibhav Borkar
 * @explanation Provide DAO layer for Role entity .
 */

package com.enotes.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
