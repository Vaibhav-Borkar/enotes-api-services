package com.enotes.category;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.exception.DataExistsException;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.utils.Validation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepo;
	private final ModelMapper mapper;
	private final Validation validation;

	@Override
	public Boolean saveCategory(CategoryDTO categoryDto) {
		
		//Validation checking..
		validation.categoryValidation(categoryDto);
		
		// Check the category already presented or not..
		Boolean exist=categoryRepo.existsByName(categoryDto.getName().trim());
		if (exist) {
			throw new DataExistsException("category already exists with name "+categoryDto.getName());
		}
		
		Category category = mapper.map(categoryDto,Category.class);
		
		category.setIsDeleted(false);
//		category.setCreatedBy(1);
//		category.setCreatedOn(new Date());
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> allCategories = categoryRepo.findByIsDeletedFalse();
		List<CategoryDTO> list = allCategories.stream().map(cat-> mapper.map(cat, CategoryDTO.class)).toList();
		return list;
	}

	@Override
	public List<CategoryResponse> getAllActiveCategory() {
		List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		return categories.stream().map(cat->mapper.map(cat,CategoryResponse.class)).toList();
	}

	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {
		Category category = categoryRepo.findByIdAndIsDeletedFalse(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with ide :"+categoryId));
		if (!ObjectUtils.isEmpty(category) ) {
			return mapper.map(category, CategoryDTO.class);
		}
		return null;
	}

	@Override
	public Boolean deletecCategoryById(Integer categoryId) {
		Optional<Category> category = categoryRepo.findById(categoryId);
		if (category.isPresent()) {
			Category presentedCategory = category.get(); 
			presentedCategory.setIsDeleted(true);
			categoryRepo.save(presentedCategory);
			return true;
		}
		return false;
	}

	@Override
	public Boolean updateCategory(Integer categoryId, CategoryDTO categoryDto) {
	Optional<Category> category = categoryRepo.findById(categoryId);
	if (category.isPresent()) {
		Category categoryObj = category.get();
		categoryObj.setName(categoryDto.getName());
		categoryObj.setUpdatedOn(new Date());
		categoryObj.setDescription(categoryDto.getDescription());
//		categoryObj.setUpdatedBy(1);
//		categoryObj.setUpdatedOn(new Date());
		categoryRepo.save(categoryObj);
		return true;
	}
		return false;
	}

}
