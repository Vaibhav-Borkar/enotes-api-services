package com.enotes.notes;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	
	@PutMapping("/{noteId}")
	public ResponseEntity<?> updateNoteHandler(@PathVariable Integer noteId ,@RequestBody NotesDTO notesDto){
	  Boolean result=notesService.updateNote(noteId,notesDto);
	  if (result) {
		return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "update success");
	}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "note id note found");
	}
	
	
	@DeleteMapping("/delete/{noteId}")
	public ResponseEntity<?> softDeleteNotesHandler(@PathVariable Integer noteId){
		Boolean result =notesService.softDeleteNotes(noteId);
		 if (result) {
			 return CommonUtil.createBuildResponseMessage(HttpStatus.OK,"delete success");
		}
		     return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "note deletion failed ! ");
				
	}


    @GetMapping ("/restore/{noteId}")
    public ResponseEntity<?> restoreNotesHandler(@PathVariable Integer noteId){
       	Boolean result =notesService.restoreNotes(noteId);
	     if (result) {
		      return CommonUtil.createBuildResponseMessage(HttpStatus.OK,"restore success");
		   }
			  return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "note deletion failed ! ");
    }
    
    
    @GetMapping("/recycle-bin/{userId}")
    public ResponseEntity<?> getRecycleBinUserHandler(@PathVariable Integer userId){
    	List<NotesDTO> recycledUser = notesService.getRecycleBinUser(userId);
    	if(CollectionUtils.isEmpty(recycledUser)) {
    		return CommonUtil.createErrorResponseMessage(HttpStatus.NO_CONTENT, "no user presentec in recycle bin");
    	}
    	return CommonUtil.createBuildResponse(recycledUser, HttpStatus.OK);
    }
    
    
    @PostMapping("/file-upload/{noteId}")
    public ResponseEntity<?> uploadFileForNoteHandler(@PathVariable Integer noteId,@RequestParam MultipartFile file) throws IOException{
    	
    	Boolean isUploaded=notesService.uploadFileForNote(noteId,file);
    	if (isUploaded) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "file uploaded successfully");
		}
    	return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "file upload failed");
    }
    
    @DeleteMapping("/delete/hard/{noteId}")
    public ResponseEntity<?> hardDeleteNotesHandler(@PathVariable Integer noteId){
        notesService.hardDeleteNotes(noteId);
        return CommonUtil.createBuildResponseMessage(HttpStatus.OK,"delete success.");
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<?> emptyRecycleBinHandler(@PathVariable Integer noteId){
    	Integer userId=1;
        notesService.emptyRecycleBin(userId);
        return CommonUtil.createBuildResponseMessage(HttpStatus.OK,"delete success.");
    }
    
    @GetMapping("/copy/{noteId}")
    public ResponseEntity<?> copyNoteHandler(@PathVariable Integer noteId){
    	Boolean copyNote = notesService.copyNote(noteId);
    	if(copyNote) {
    		return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED,"copied success");   		
    	}
    	return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "copy failed . Try again !");
    }
    
}


