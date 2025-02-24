package com.enotes.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.enotes.category.CategoryDTO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Validation {

	public void categoryValidation(CategoryDTO categoryDto) {

		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category object should not be blank or empty");
		} else {

			// Validation for name field.
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name cannot be empty or null");
			} else {
				if (categoryDto.getName().length() < 10) {
					error.put("name", "name min length is 10 ");
				}
				if (categoryDto.getName().length() > 100) {
					error.put("name", "name max length is 10 ");
				}
			}

			// Validation for description field.

			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description cannot be empty or null");
			}

			
			// Validation for isActive field.
            
			if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				System.err.print(categoryDto.getIsActive());
				error.put("isActive", "name cannot be empty or null");
			} else {
				if (categoryDto.getIsActive() == null || (!(categoryDto.getIsActive() instanceof Boolean))) {
					error.put("isActive", "isActive must be a boolean value (true or false)");
				}
			}
		}
		if (!error.isEmpty()) {
			throw new com.enotes.exception.ValidationException(error);
		}
	}
}
