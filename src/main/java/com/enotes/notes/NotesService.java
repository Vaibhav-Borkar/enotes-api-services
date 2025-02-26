package com.enotes.notes;

import java.util.List;

public interface NotesService {

	public Boolean saveNotes(NotesDTO notesDTO);
	
	public List<NotesDTO> getAllNotes();
}
