package com.enotes.favourite;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.utils.CommonUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/notes")
@AllArgsConstructor
public class FavouriteNoteController {

	
	private final FavouriteNoteService favouriteNoteService;
	
	
	
	@PostMapping("/fav/{noteId}")
	public ResponseEntity<?> favouriteNoteHandler(@PathVariable Integer noteId){
		favouriteNoteService.favouriteNote(noteId);
		return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED,"note added favourite");
	}
	
	@DeleteMapping("/un-fav/{noteId}")
	public ResponseEntity<?> unFavouriteNoteHandler(@PathVariable Integer noteId){
		favouriteNoteService.unFavouriteNote(noteId);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "note removed from favourite");
	}
	
	@GetMapping("/fav")
	public ResponseEntity<?> getUserFavouriteNotesHandler(){
		List<FavouriteNoteDTO> userFavouriteNotes = favouriteNoteService.getUserFavouriteNotes();
		return CommonUtil.createBuildResponse(userFavouriteNotes, HttpStatus.OK);
	}
}


