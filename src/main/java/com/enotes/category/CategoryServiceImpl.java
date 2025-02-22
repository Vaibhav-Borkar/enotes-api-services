package com.enotes.category;

import java.util.Date;
import java.util.List;

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
		List<Category> allCategories = categoryRepo.findAll();
		List<CategoryDTO> list = allCategories.stream().map(cat-> mapper.map(cat, CategoryDTO.class)).toList();
		return list;
	}

	@Override
	public List<CategoryResponse> getAllActiveCategory() {
		List<Category> categories = categoryRepo.findByIsActiveTrue();
		return categories.stream().map(cat->mapper.map(cat,CategoryResponse.class)).toList();
	}

}
