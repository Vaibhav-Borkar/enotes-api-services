package com.enotes.favourite;

import java.util.List;

public interface FavouriteNoteService {

	public void favouriteNote(Integer noteId);
	
	public void unFavouriteNote(Integer noteId);
	
	public List<FavouriteNoteDTO> getUserFavouriteNotes();

}
