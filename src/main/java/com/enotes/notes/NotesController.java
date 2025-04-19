package com.enotes.notes;

import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.enotes.file.FileDetails;
import com.enotes.utils.CommonUtil;
import com.enotes.utils.FileDetailsUtil;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class NotesController implements NotesEndpoint {

	private final NotesService notesService;

	@Override
	public ResponseEntity<?> saveNotesHandler(String notes, MultipartFile file) throws Exception {
		Boolean saveNotes = notesService.saveNotes(notes, file);
		if (saveNotes) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED, "notes saved success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "notes not saved");
	}

	@Override
	public ResponseEntity<?> getAllNotesHandler() {
		List<NotesDTO> allNotes = notesService.getAllNotes();
		if (!CollectionUtils.isEmpty(allNotes)) {
			return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<?> downloadFileHandler(Integer id) throws Exception {
		FileDetails details = notesService.getFileDetails(id);
		byte[] fileData = notesService.downloadFile(details);
		String contentType = FileDetailsUtil.getContentType(details.getOriginalFileName());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", details.getOriginalFileName());

		return ResponseEntity.ok().headers(headers).body(fileData);
	}

	@Override
	public ResponseEntity<?> getAllNotesByUserHandler(Integer pageNumber, Integer pageSize) {
		NotesResponse allNotes = notesService.getAllNotesByUser(pageNumber, pageSize);
		if (ObjectUtils.isEmpty(allNotes)) {
			return CommonUtil.createErrorResponseMessage(HttpStatus.NO_CONTENT, "no content found");
		}
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateNoteHandler(Integer noteId, NotesDTO notesDto) {
		Boolean result = notesService.updateNote(noteId, notesDto);
		if (result) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "update success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "note id note found");
	}

	@Override
	public ResponseEntity<?> softDeleteNotesHandler(Integer noteId) {
		Boolean result = notesService.softDeleteNotes(noteId);
		if (result) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "delete success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "note deletion failed ! ");

	}

	@Override
	public ResponseEntity<?> restoreNotesHandler(Integer noteId) {
		Boolean result = notesService.restoreNotes(noteId);
		if (result) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "restore success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "note deletion failed ! ");
	}

	@Override
	public ResponseEntity<?> getRecycleBinUserHandler() {
		List<NotesDTO> recycledUser = notesService.getRecycleBinUser();
		if (CollectionUtils.isEmpty(recycledUser)) {
			return CommonUtil.createErrorResponseMessage(HttpStatus.NO_CONTENT, "no user presentec in recycle bin");
		}
		return CommonUtil.createBuildResponse(recycledUser, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> uploadFileForNoteHandler(Integer noteId, MultipartFile file) throws IOException {

		Boolean isUploaded = notesService.uploadFileForNote(noteId, file);
		if (isUploaded) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "file uploaded successfully");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "file upload failed");
	}

	@Override
	public ResponseEntity<?> hardDeleteNotesHandler(Integer noteId) {
		notesService.hardDeleteNotes(noteId);
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "delete success.");
	}

	@Override
	public ResponseEntity<?> emptyRecycleBinHandler(Integer noteId) {
		notesService.emptyRecycleBin();
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "delete success.");
	}

	@Override
	public ResponseEntity<?> copyNoteHandler(Integer noteId) {
		Boolean copyNote = notesService.copyNote(noteId);
		if (copyNote) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED, "copied success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "copy failed . Try again !");
	}

	@Override
	public ResponseEntity<?> getNotesByUserSearchHandler(String keyword, Integer pageNumber, Integer pageSize) {
		NotesResponse notes = notesService.getNotesByUserSearch(pageNumber, pageSize, keyword);
		if (ObjectUtils.isEmpty(notes)) {
			CommonUtil.createBuildResponseMessage(HttpStatus.NOT_FOUND, "data not found for this ");
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}

}
