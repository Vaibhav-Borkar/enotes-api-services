package com.enotes.category;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

	Boolean saveCategory(CategoryDTO catergoryDTO);
	
	List<CategoryDTO> getAllCategory();

	List<CategoryResponse> getAllActiveCategory();

	CategoryDTO getCategoryById(Integer categoryId);

	Boolean deletecCategoryById(Integer categoryId);

	Boolean updateCategory(Integer categoryId, CategoryDTO categoryDto);
}
