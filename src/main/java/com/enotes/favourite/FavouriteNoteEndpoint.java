package com.enotes.favourite;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/notes")
public interface FavouriteNoteEndpoint {

	@PostMapping("/fav/{noteId}")
	public ResponseEntity<?> favouriteNoteHandler(@PathVariable Integer noteId);
	
	@DeleteMapping("/un-fav/{noteId}")
	public ResponseEntity<?> unFavouriteNoteHandler(@PathVariable Integer noteId);
	
	@GetMapping("/fav")
	public ResponseEntity<?> getUserFavouriteNotesHandler();
}
