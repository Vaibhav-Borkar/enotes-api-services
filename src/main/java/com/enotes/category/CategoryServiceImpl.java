package com.enotes.category;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepo;
	private final ModelMapper mapper;

	@Override
	public Boolean saveCategory(CategoryDTO categoryDto) {
		
		Category category = mapper.map(categoryDto,Category.class);
		
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
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
		Optional<Category> category = categoryRepo.findByIdAndIsDeletedFalse(categoryId);
		if (category.isPresent()) {
			return mapper.map(category.get(), CategoryDTO.class);
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

}
