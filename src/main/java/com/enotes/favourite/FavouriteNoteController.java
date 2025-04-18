package com.enotes.favourite;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.enotes.utils.CommonUtil;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class FavouriteNoteController implements FavouriteNoteEndpoint {

	
	private final FavouriteNoteService favouriteNoteService;
	
	@Override
	public ResponseEntity<?> favouriteNoteHandler( Integer noteId){
		favouriteNoteService.favouriteNote(noteId);
		return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED,"note added favourite");
	}
	
	@Override
	public ResponseEntity<?> unFavouriteNoteHandler( Integer noteId){
		favouriteNoteService.unFavouriteNote(noteId);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "note removed from favourite");
	}
	
	@Override
	public ResponseEntity<?> getUserFavouriteNotesHandler(){
		List<FavouriteNoteDTO> userFavouriteNotes = favouriteNoteService.getUserFavouriteNotes();
		return CommonUtil.createBuildResponse(userFavouriteNotes, HttpStatus.OK);
	}
}


