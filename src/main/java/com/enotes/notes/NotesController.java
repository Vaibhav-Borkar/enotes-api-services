package com.enotes.notes;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.file.FileDetails;
import com.enotes.utils.CommonUtil;
import com.enotes.utils.FileDetailsUtil;

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
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFileHandler(@PathVariable Integer id) throws Exception{
		  FileDetails details = notesService.getFileDetails(id);
		  byte[] fileData = notesService.downloadFile(details);
		  String contentType = FileDetailsUtil.getContentType(details.getOriginalFileName());
	     HttpHeaders headers = new HttpHeaders(); 
	     headers.setContentType(MediaType.parseMediaType(contentType));
	     headers.setContentDispositionFormData("attachment",details.getOriginalFileName());
	     
	     return ResponseEntity.ok().headers(headers).body(fileData);
	}
	
	@GetMapping("/user-notes")
	public ResponseEntity<?> getAllNotesByUserHandler(
		@RequestParam (defaultValue = "0") Integer pageNumber,
		@RequestParam (defaultValue = "5") Integer pageSize
			){
		Integer userId=1;
		NotesResponse allNotes = notesService.getAllNotesByUser(userId,pageNumber,pageSize);
		if(ObjectUtils.isEmpty(allNotes)) {
			return CommonUtil.createErrorResponseMessage(HttpStatus.NO_CONTENT, "no content found");
		}
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}
	
}
