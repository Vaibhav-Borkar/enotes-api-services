package com.enotes.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enotes.notes.Notes;
import com.enotes.notes.NotesRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NotesSchedular {

	private final NotesRepository notesRepo;

	@Scheduled(cron = "0 0 12 * * ?")
	public void deleteNoteSchedular() {
	 // Automatically delete notes after 7 days.
		LocalDateTime cutOffDays = LocalDateTime.now().minusDays(7);
		List<Notes> deleteNotes = notesRepo.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDays);
		notesRepo.deleteAll(deleteNotes);
		
	}
}
