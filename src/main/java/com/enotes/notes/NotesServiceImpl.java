package com.enotes.notes;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.enotes.category.Category;
import com.enotes.category.CategoryRepository;
import com.enotes.exception.CategoryNotFoundException;
import com.enotes.notes.NotesDTO.CategoryDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotesServiceImpl implements NotesService {

	private final NotesRepository notesRepo;
	private CategoryRepository categoryRepo;
	private final ModelMapper mapper;
	

	@Override
	public Boolean saveNotes(NotesDTO notesDTO) {
		
		// For checking the provided category is presented or not.
		checkCategoryExists(notesDTO.getCategory());
		
		Notes notes = mapper.map(notesDTO, Notes.class);
		Notes savedNote = notesRepo.save(notes);
		if(ObjectUtils.isEmpty(savedNote)) {
			return false;
		}
		return true;
	}

	private void checkCategoryExists(CategoryDTO category) {
		 Optional<Category> categoryId = categoryRepo.findById(category.getId());
		 if(!categoryId.isPresent()) {
			 throw new CategoryNotFoundException("category not found with id : "+category.getId());
		 }
		
	}

	@Override
	public List<NotesDTO> getAllNotes() {
		
		List<NotesDTO> notesList = notesRepo.findAll().stream().map(notes->mapper.map(notes, NotesDTO.class)).toList();
		if(!CollectionUtils.isEmpty(notesList)) {
			return notesList;
		}
		return null;
	}

}
