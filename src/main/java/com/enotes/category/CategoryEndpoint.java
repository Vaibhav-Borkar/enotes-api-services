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

import com.enotes.utils.SWAGGER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.enotes.utils.Constants.ROLE_ADMIN;
import static com.enotes.utils.Constants.ROLE_ADMIN_USER;
import static com.enotes.utils.Constants.ROLE_USER;

@Tag(name = "Category APIs",description = "This controller contains the APIs related to category module")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

	@Operation(summary = "Save Category",description = SWAGGER.SAVE_CATEGORY)
	@PreAuthorize(ROLE_ADMIN)
	@PostMapping
	public ResponseEntity<?> saveCategoryHandler(@RequestBody CategoryDTO category);
	
	@Operation(summary = "Get All Category",description = SWAGGER.GET_ALL_CATEGORY)
	@GetMapping
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getAllCategoryHandler();
	
	@Operation(summary = "Active Category",description = SWAGGER.ACTIVE_CATEGORY)
	@GetMapping("/active-category")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getActiveCategoryHandler();
	
	@Operation(summary = "Get Category",description = SWAGGER.GET_CATEGORY_BY_ID)
	@PreAuthorize(ROLE_ADMIN)
	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getCategoryByIdHandler(@PathVariable Integer categoryId);
	
	@Operation(summary = "Delete Category",description = SWAGGER.DELETE_CATEGORY)
	@PreAuthorize(ROLE_ADMIN)
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategoryByIdHandler(@PathVariable Integer categoryId);
	
	@Operation(summary = "Update Category",description = SWAGGER.UPDATE_CATEGORY)
	@PreAuthorize(ROLE_ADMIN)
	@PutMapping("/{categoryId}")
	public ResponseEntity<?> updateCategoryHandler(@RequestBody CategoryDTO categoryDto, @PathVariable Integer categoryId);
	
	
}
