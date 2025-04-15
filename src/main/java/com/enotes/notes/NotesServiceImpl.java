package com.enotes.notes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.file.FileDetails;
import com.enotes.file.FileDetailsRepository;
import com.enotes.utils.CommonUtil;
import com.enotes.utils.FileDetailsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {

	private final NotesRepository notesRepo;
	private final ModelMapper mapper;
	private final ObjectMapper objectMapper;
	private final FileDetailsRepository fileDetailsRepo;
	private final FileDetailsUtil fileDetailsUtil; 

	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Override
	public Boolean saveNotes(String notes,MultipartFile file) throws Exception{
		
		NotesDTO notesDto = objectMapper.readValue(notes,NotesDTO.class);
		// For checking the provided category is presented or not.
		fileDetailsUtil.checkCategoryExists(notesDto.getCategory());
		Notes notesData = mapper.map(notesDto, Notes.class);
		notesData.setIsDeleted(false);
		FileDetails fileDetails = fileDetailsUtil.saveFileDetails(file,uploadPath);
		if (!ObjectUtils.isEmpty(fileDetails)) {
//			notesData.setFileDetails(fileDetails);
			fileDetails.setNotes(notesData); // 🔥 This was missing
			notesData.setFileDetails(Arrays.asList(fileDetails));
		}
		else {
			notesData.setFileDetails(null);
		}
		
		Notes savedNote = notesRepo.save(notesData);
		if(ObjectUtils.isEmpty(savedNote)) {
			return false;
		}
		return true;
	} 


	@Override
	public List<NotesDTO> getAllNotes() {
		
//		List<NotesDTO> notesList = notesRepo.findAll().stream().map(notes->mapper.map(notes, NotesDTO.class)).toList();
		List<NotesDTO> notesList = notesRepo.findByIsDeletedFalse().stream().map(notes->mapper.map(notes, NotesDTO.class)).toList();
		if(!CollectionUtils.isEmpty(notesList)) {
			return notesList;
		}
		return null;
	}


	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		
		InputStream inputStream = new FileInputStream(fileDetails.getPath());
		return StreamUtils.copyToByteArray(inputStream);
	}


	@Override
	public FileDetails getFileDetails(Integer id) {
		return fileDetailsRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("file is not avaible for id"+id));
	}


	@Override
	public NotesResponse getAllNotesByUser(Integer pageNumber,Integer pageSize) {
		Integer userId=CommonUtil.getLoggedInUser().getId();
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
//	    Page<Notes> pageNotes = notesRepo.findByCreatedBy(userId,pageable);
		Page<Notes> pageNotes = notesRepo.findByCreatedByAndIsDeletedFalse(userId,pageable);
			    
	    List<NotesDTO> notesDto = pageNotes.get().map(note->mapper.map(note, NotesDTO.class)).toList();
	    
	    NotesResponse response = NotesResponse.builder()
	    		.notes(notesDto)
	    		.pageNo(pageNotes.getNumber())
	    		.pageSize(pageNotes.getSize())
	    		.totalPages(pageNotes.getTotalPages())
	    		.totalElements(pageNotes.getTotalElements())
	    		.isFirst(pageNotes.isFirst())
	    		.isLast(pageNotes.isLast())
	    		.build();
		return response;
	}


	@Override
	public Boolean updateNote(Integer noteId, NotesDTO notesDto) {
		Notes notes = notesRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("note not found for this id "));
		if(!ObjectUtils.isEmpty(notes)) {
			notes.setTitle(notesDto.getTitle());
			notes.setDescription(notesDto.getDescription());
			notesRepo.save(notes);
			return true;
		}
		return false;
	}


	@Override
	public Boolean softDeleteNotes(Integer noteId) {
		Notes notes = notesRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("note not found for this id "));
		if(!ObjectUtils.isEmpty(notes)) {
		notes.setIsDeleted(true);
		notes.setDeletedOn(LocalDateTime.now());
		notesRepo.save(notes);
		return true;
		}
		return false;
	}


	@Override
	public Boolean restoreNotes(Integer noteId) {
		Notes notes = notesRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("note not found for this id "));
		if(!ObjectUtils.isEmpty(notes)) {
			notes.setIsDeleted(false);
			notes.setDeletedOn(null);
			notesRepo.save(notes);
			return true;
			}
			return false;
	}


	@Override
	public List<NotesDTO> getRecycleBinUser() {
		Integer userId=CommonUtil.getLoggedInUser().getId();
		List<Notes> notes =notesRepo.findByCreatedByAndIsDeletedTrue(userId);
		System.out.print(notes);
		return notes.stream().map(note-> mapper.map(note, NotesDTO.class)).toList();
	}


	@Override
	public Boolean uploadFileForNote(Integer noteId, MultipartFile file) throws IOException {
		Notes note = notesRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("note not found for this id"));
		FileDetails savedFile = fileDetailsUtil.saveFileDetails(file, uploadPath);
		if(savedFile==null) {
			throw new IOException("failed to save file.");
		}
		List<FileDetails> existingFiles = note.getFileDetails();
		if (existingFiles == null) {
	        existingFiles = new ArrayList<>();
	    }
		existingFiles.add(savedFile);
		note.setFileDetails(existingFiles);
	    savedFile.setNotes(note);
	    
	    fileDetailsRepo.save(savedFile);
	    notesRepo.save(note);
		return true;
	}


	@Override
	public void hardDeleteNotes(Integer noteId) {
		Notes note = notesRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Not not not found for this id "));
		if (note.getIsDeleted()) {
			notesRepo.delete(note);
		}
		throw new IllegalArgumentException("soory you can`t delete directly.");
	}


	@Override
	public void emptyRecycleBin() {
		Integer userId=CommonUtil.getLoggedInUser().getId();
		List<Notes> notes =notesRepo.findByCreatedByAndIsDeletedTrue(userId);
		if(!CollectionUtils.isEmpty(notes)) {
			notesRepo.deleteAll();
		}
		
	}


	@Override
	public Boolean copyNote(Integer noteId) {
		Notes note = notesRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Not not not found for this id "));
		
		Notes copyNote = Notes.builder()
				.category(note.getCategory())
				.description(note.getDescription())
				.title(note.getTitle())
				.isDeleted(false)
				.build();
		Notes saveCopyNote = notesRepo.save(copyNote);
		if(!ObjectUtils.isEmpty(saveCopyNote)) {
			return true;
		}
		return false;
	}


	@Override
	public NotesResponse getNotesByUserSearch(Integer pageNumber, Integer pageSize,String keyword) {
		Integer userId=CommonUtil.getLoggedInUser().getId();
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		Page<Notes> pageNotes = notesRepo.searchNotes(keyword,userId,pageable);
			    
	    List<NotesDTO> notesDto = pageNotes.get().map(note->mapper.map(note, NotesDTO.class)).toList();
	    
	    NotesResponse response = NotesResponse.builder()
	    		.notes(notesDto)
	    		.pageNo(pageNotes.getNumber())
	    		.pageSize(pageNotes.getSize())
	    		.totalPages(pageNotes.getTotalPages())
	    		.totalElements(pageNotes.getTotalElements())
	    		.isFirst(pageNotes.isFirst())
	    		.isLast(pageNotes.isLast())
	    		.build();
		return response;
	}

}
