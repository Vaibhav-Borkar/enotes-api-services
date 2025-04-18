package com.enotes.notes;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import static com.enotes.utils.Constants.ROLE_ADMIN;
import static com.enotes.utils.Constants.ROLE_ADMIN_USER;
import static com.enotes.utils.Constants.ROLE_USER;
import static com.enotes.utils.Constants.DEFAULT_PAGE_NO;
import static com.enotes.utils.Constants.DEFAULT_PAGE_SIZE;
@RequestMapping("/api/v1/notes")
public interface NoteEndpoint {

	@PostMapping
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotesHandler(@RequestParam String notes,
			@RequestParam(required = false) MultipartFile file) throws Exception;
	
	@GetMapping
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesHandler();
	
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> downloadFileHandler(@PathVariable Integer id) throws Exception ;
	
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUserHandler(@RequestParam(defaultValue = DEFAULT_PAGE_NO) Integer pageNumber,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
	@PutMapping("/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> updateNoteHandler(@PathVariable Integer noteId, @RequestBody NotesDTO notesDto);
	
	@DeleteMapping("/delete/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> softDeleteNotesHandler(@PathVariable Integer noteId) ;
	
	@GetMapping("/restore/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotesHandler(@PathVariable Integer noteId);
	
	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getRecycleBinUserHandler();
	
	@PostMapping("/file-upload/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> uploadFileForNoteHandler(@PathVariable Integer noteId, @RequestParam MultipartFile file)
			throws IOException;
	
	@DeleteMapping("/delete/hard/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotesHandler(@PathVariable Integer noteId);
	
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyRecycleBinHandler(@PathVariable Integer noteId);
	
	@GetMapping("/copy/{noteId}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> copyNoteHandler(@PathVariable Integer noteId);
	
	@GetMapping("/search")
	 @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> getNotesByUserSearchHandler(
    		@RequestParam (defaultValue = "")String keyword,
    		@RequestParam (defaultValue = DEFAULT_PAGE_NO) Integer pageNumber,
    		@RequestParam (defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
}
