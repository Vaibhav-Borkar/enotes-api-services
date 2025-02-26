package com.enotes.notes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.category.Category;
import com.enotes.category.CategoryRepository;
import com.enotes.exception.CategoryNotFoundException;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.UnSupportedFileException;
import com.enotes.file.FileDetails;
import com.enotes.file.FileDetailsRepository;
import com.enotes.notes.NotesDTO.CategoryDTO;
import com.enotes.utils.FileDetailsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {

	private final NotesRepository notesRepo;
	private final CategoryRepository categoryRepo;
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
		
		FileDetails fileDetails = fileDetailsUtil.saveFileDetails(file,uploadPath);
		if (!ObjectUtils.isEmpty(fileDetails)) {
			notesData.setFileDetails(fileDetails);
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
		
		List<NotesDTO> notesList = notesRepo.findAll().stream().map(notes->mapper.map(notes, NotesDTO.class)).toList();
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

}
