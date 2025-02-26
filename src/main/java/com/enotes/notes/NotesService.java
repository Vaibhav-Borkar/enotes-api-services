package com.enotes.notes;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface NotesService {

	public Boolean saveNotes(String notes,MultipartFile file)throws Exception;
	
	public List<NotesDTO> getAllNotes();
}
