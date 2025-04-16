package com.enotes.notes;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.file.FileDetails;

public interface NotesService {

	public Boolean saveNotes(String notes,MultipartFile file)throws Exception;
	
	public List<NotesDTO> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id);

	public NotesResponse getAllNotesByUser(Integer pageNumber,Integer pageSize);

	public Boolean updateNote(Integer noteId, NotesDTO notesDto);

	public Boolean softDeleteNotes(Integer noteId);

	public Boolean restoreNotes(Integer noteId);

	public List<NotesDTO> getRecycleBinUser();

	public Boolean uploadFileForNote(Integer noteId, MultipartFile file) throws IOException;

	public void hardDeleteNotes(Integer noteId);

	public void emptyRecycleBin();

	public Boolean copyNote(Integer noteId);
	
	public NotesResponse getNotesByUserSearch(Integer pageNumber,Integer pageSize,String keyword);

}
