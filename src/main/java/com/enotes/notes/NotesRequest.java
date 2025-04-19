package com.enotes.notes;

import com.enotes.notes.NotesDTO.CategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotesRequest {

	private String title;

	private String description;

	private CategoryDTO category;
	
	
}
