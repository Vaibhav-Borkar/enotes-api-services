package com.enotes.category;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.enotes.utils.Constants.ROLE_ADMIN;
import static com.enotes.utils.Constants.ROLE_ADMIN_USER;
import static com.enotes.utils.Constants.ROLE_USER;


@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

	@PreAuthorize(ROLE_ADMIN)
	@PostMapping
	public ResponseEntity<?> saveCategoryHandler(@RequestBody CategoryDTO category);
	
	@GetMapping
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getAllCategoryHandler();
	
	@GetMapping("/active-category")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getActiveCategoryHandler();
	
	@PreAuthorize(ROLE_ADMIN)
	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getCategoryByIdHandler(@PathVariable Integer categoryId);
	
	@PreAuthorize(ROLE_ADMIN)
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategoryByIdHandler(@PathVariable Integer categoryId);
	
	@PreAuthorize(ROLE_ADMIN)
	@PutMapping("/{categoryId}")
	public ResponseEntity<?> updateCategoryHandler(@RequestBody CategoryDTO categoryDto, @PathVariable Integer categoryId);
	
	
}
