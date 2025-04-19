/**
 * @author Vaibhav Borkar
 * @explanation : This class contains the all end-points of NotesController . We defining the notes endpoint here for better readability and code maintainability,we also using the swagger for providing the better client api specification for use so any user can easily understand out apis. 
 * @important We can also use specify the status code for swagger using @ApiResponse
 */

package com.enotes.notes;

import java.io.IOException;

import org.springframework.http.MediaType;
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

import com.enotes.utils.SWAGGER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.enotes.utils.Constants.ROLE_ADMIN;
import static com.enotes.utils.Constants.ROLE_ADMIN_USER;
import static com.enotes.utils.Constants.ROLE_USER;
import static com.enotes.utils.Constants.DEFAULT_PAGE_NO;
import static com.enotes.utils.Constants.DEFAULT_PAGE_SIZE;

@Tag(name = "Note APIs", description = "This controller contains the APIs related to Note module")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

	@Operation(summary = "Save Note", description = SWAGGER.SAVE_NOTE)
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotesHandler(
			@RequestParam @Parameter(description = "Json String Notes", required = true, content = @Content(schema = @Schema(implementation = NotesRequest.class))) String notes,
			@RequestParam(required = false) MultipartFile file) throws Exception;

	@Operation(summary = "Get All Notes", description = SWAGGER.GET_ALL_NOTES)
	@GetMapping
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesHandler();

	@Operation(summary = "Download File", description = SWAGGER.DOWNLOAD_FILE)
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> downloadFileHandler(@PathVariable Integer id) throws Exception;

	@Operation(summary = "Get User Note", description = SWAGGER.GET_USER_NOTE)
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUserHandler(@RequestParam(defaultValue = DEFAULT_PAGE_NO) Integer pageNumber,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

	@Operation(summary = "Update Note", description = SWAGGER.UPDATE_NOTE)
	@PutMapping("/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> updateNoteHandler(@PathVariable Integer noteId, @RequestBody NotesDTO notesDto);

	@Operation(summary = "Soft Delete Note", description = SWAGGER.SOFT_DELETE_NOTE)
	@DeleteMapping("/delete/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> softDeleteNotesHandler(@PathVariable Integer noteId);

	@Operation(summary = "Restore Note", description = SWAGGER.RESTORE_NOTE)
	@GetMapping("/restore/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotesHandler(@PathVariable Integer noteId);

	@Operation(summary = "Note Recycle Bin", description = SWAGGER.NOTE_RECYCLE_BIN)
	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getRecycleBinUserHandler();

	@Operation(summary = "File Upload", description = SWAGGER.FILE_UPLOAD)
	@PostMapping(value="/file-upload/{noteId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> uploadFileForNoteHandler(@PathVariable Integer noteId, @RequestParam MultipartFile file)
			throws IOException;

	@Operation(summary = "Hard Delete Note", description = SWAGGER.HARD_DELETE_NOTE)
	@DeleteMapping("/delete/hard/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotesHandler(@PathVariable Integer noteId);

	@Operation(summary = "Empty Recycle Bin", description = SWAGGER.EMPTY_RECYCLE_BIN)
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyRecycleBinHandler(@PathVariable Integer noteId);

	@Operation(summary = "Copy Note", description = SWAGGER.COPY_NOTE)
	@GetMapping("/copy/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNoteHandler(@PathVariable Integer noteId);

	@Operation(summary = "Search Note", description = SWAGGER.SEARCH_NOTE)
	@GetMapping("/search")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getNotesByUserSearchHandler(@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = DEFAULT_PAGE_NO) Integer pageNumber,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

}
