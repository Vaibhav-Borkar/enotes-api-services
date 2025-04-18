package com.enotes.category;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import com.enotes.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class CategoryController implements CategoryEndpoint {

	private final CategoryService categoryService;
	
	@Override
	public ResponseEntity<?> saveCategoryHandler( CategoryDTO category) {
		log.info("CategoryController :: saveCategoryHandler");
		Boolean saveCategory = categoryService.saveCategory(category);
		if(saveCategory) {
			return CommonUtil.createBuildResponse("saved success", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR,"category not saved");
	}
	
	@Override
	public ResponseEntity<?> getAllCategoryHandler(){
		log.info("CategoryController :: getAllCategoryHandler");
		List<CategoryDTO> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getActiveCategoryHandler(){
		log.info("CategoryController :: getActiveCategoryHandler");
		List<CategoryResponse> allActiveCategories=categoryService.getAllActiveCategory();
		if(CollectionUtils.isEmpty(allActiveCategories)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(allActiveCategories, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getCategoryByIdHandler( Integer categoryId){
		log.info("CategoryController :: gerCategoryByIdHandler");
		CategoryDTO categoryDto= categoryService.getCategoryById(categoryId);
		if(ObjectUtils.isEmpty(categoryDto)) {
			return CommonUtil.createErrorResponseMessage(HttpStatus.NOT_FOUND,"Internal server error");
		}
		return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteCategoryByIdHandler( Integer categoryId){
		log.info("CategoryController :: deleteCategoryByIdHandler");
		Boolean deletedCategory = categoryService.deletecCategoryById(categoryId);
		if(deletedCategory) {
			return CommonUtil.createBuildResponse("category deleted success"+categoryId, HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR,"category not deleted" );
	}
	
	@Override
	public ResponseEntity<?> updateCategoryHandler( CategoryDTO categoryDto,  Integer categoryId){
		Boolean updatedCategory = categoryService.updateCategory(categoryId,categoryDto);
		if(updatedCategory) {
			return CommonUtil.createBuildResponse("update success", HttpStatus.OK);
		}
		return CommonUtil.createErrorResponse("category not found with id : "+ categoryId, HttpStatus.NOT_FOUND);
	}
}







