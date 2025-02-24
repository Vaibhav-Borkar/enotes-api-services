package com.enotes.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
@Slf4j
public class CategoryController {

	private final CategoryService categoryService;
	
	
	@PostMapping
	public ResponseEntity<?> saveCategoryHandler(@RequestBody CategoryDTO category) {
		log.info("CategoryController :: saveCategoryHandler");
		Boolean saveCategory = categoryService.saveCategory(category);
		if(saveCategory) {
			return new ResponseEntity<>("saved success",HttpStatus.CREATED);
		}
		return new ResponseEntity<> ("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllCategoryHandler(){
		log.info("CategoryController :: getAllCategoryHandler");
		List<CategoryDTO> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		return new ResponseEntity<>(allCategory,HttpStatus.OK);
	}
	
	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategoryHandler(){
		log.info("CategoryController :: getActiveCategoryHandler");
		List<CategoryResponse> allActiveCategories=categoryService.getAllActiveCategory();
		if(CollectionUtils.isEmpty(allActiveCategories)) {
			return ResponseEntity.noContent().build();
		}
		return new ResponseEntity<>(allActiveCategories,HttpStatus.OK);
	}
	
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getCategoryByIdHandler(@PathVariable Integer categoryId){
		log.info("CategoryController :: gerCategoryByIdHandler");
		CategoryDTO categoryDto= categoryService.getCategoryById(categoryId);
		if(ObjectUtils.isEmpty(categoryDto)) {
			return new ResponseEntity<>("Internal server error : ",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategoryByIdHandler(@PathVariable Integer categoryId){
		log.info("CategoryController :: deleteCategoryByIdHandler");
		Boolean deletedCategory = categoryService.deletecCategoryById(categoryId);
		if(deletedCategory) {
			return new ResponseEntity<>("category deleted success"+categoryId,HttpStatus.OK);
		}
		return new ResponseEntity<>("category not deleted ",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}







