package com.enotes.notes;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.utils.CommonUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/notes")
@AllArgsConstructor
public class NotesController {

	private final NotesService notesService;
	
	@PostMapping
	public ResponseEntity<?> saveNotesHandler(@RequestParam String notes,@RequestParam (required = false)MultipartFile file) throws Exception{
		Boolean saveNotes = notesService.saveNotes(notes,file);
		if(saveNotes) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED,"notes saved success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR,"notes not saved");
	}
	
	@GetMapping
	public ResponseEntity<?> getAllNotesHandler(){
		List<NotesDTO> allNotes = notesService.getAllNotes();
		if(!CollectionUtils.isEmpty(allNotes)) {
			return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
}
