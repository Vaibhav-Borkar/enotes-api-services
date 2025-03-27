package com.enotes.favourite;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.enotes.exception.ResourceNotFoundException;
import com.enotes.notes.Notes;
import com.enotes.notes.NotesRepository;
import com.enotes.utils.CommonUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FavouriteNoteServiceImpl implements FavouriteNoteService {

	private final NotesRepository notesRepo;
	private final FavouriteNoteRepository favouriteNoteRepo;
	private final ModelMapper mapper;
	
	@Override
	public void favouriteNote(Integer noteId) {
		Integer userId=CommonUtil.getLoggedInUser().getId();
		Notes note = notesRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("note not found for this id"));
		
		 // Check if the note is already marked as favorite by the user
	    boolean alreadyFavourite = favouriteNoteRepo.findByUserId(userId).stream()
	            .anyMatch(favNote -> favNote.getNote().getId().equals(noteId));
		
	    if (!alreadyFavourite) {
	        FavouriteNote favouriteNote = FavouriteNote.builder()
	                .note(note)
	                .userId(userId)
	                .build();
	        favouriteNoteRepo.save(favouriteNote);
	    } else {
	        throw new IllegalStateException("Note is already marked as favorite.");
	    }
		
	}

	@Override
	public void unFavouriteNote(Integer favNoteId) {
		FavouriteNote favNote = favouriteNoteRepo.findById(favNoteId).orElseThrow(()-> new ResourceNotFoundException("favNoteId not found in favourite notes"));
		favouriteNoteRepo.delete(favNote);
		
	}

	@Override
	public List<FavouriteNoteDTO> getUserFavouriteNotes() {
		Integer userId=CommonUtil.getLoggedInUser().getId();
		List<FavouriteNote> notes = favouriteNoteRepo.findByUserId(userId); 
		
		if(CollectionUtils.isEmpty(notes)) {
			throw new ResourceNotFoundException("favourite note not availble");
		}
		return notes.stream().map(note-> mapper.map(note, FavouriteNoteDTO.class)).toList();
		
	}

}
