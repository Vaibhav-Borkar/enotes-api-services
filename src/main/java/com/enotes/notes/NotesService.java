package com.enotes.notes;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.file.FileDetails;

public interface NotesService {

	public Boolean saveNotes(String notes,MultipartFile file)throws Exception;
	
	public List<NotesDTO> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id);

	public NotesResponse getAllNotesByUser(Integer userId,Integer pageNumber,Integer pageSize);
}
