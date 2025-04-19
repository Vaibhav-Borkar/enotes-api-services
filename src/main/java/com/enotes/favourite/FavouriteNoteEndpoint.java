package com.enotes.favourite;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.utils.SWAGGER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FavouriteNote APIs",description = "This controller contains the APIs related to favourite note module")
@RequestMapping("/api/v1/notes")
public interface FavouriteNoteEndpoint {

	@Operation(summary = "Add Favourite Note",description = SWAGGER.ADD_FAVOURITE_NOTE)
	@PostMapping("/fav/{noteId}")
	public ResponseEntity<?> favouriteNoteHandler(@PathVariable Integer noteId);
	
	@Operation(summary = "Un Favourite Note",description = SWAGGER.UN_FAVOURITE_NOTE)
	@DeleteMapping("/un-fav/{noteId}")
	public ResponseEntity<?> unFavouriteNoteHandler(@PathVariable Integer noteId);
	
	@Operation(summary = "Get Favourite Note",description = SWAGGER.GET_USER_FAVOURITE_NOTE)
	@GetMapping("/fav")
	public ResponseEntity<?> getUserFavouriteNotesHandler();
}
