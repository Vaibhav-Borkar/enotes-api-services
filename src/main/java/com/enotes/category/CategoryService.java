package com.enotes.category;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

	Boolean saveCategory(Category catergory);
	
	List<Category> getAllCategory();
}
