package com.enotes.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.enotes.category.Category;
import com.enotes.category.CategoryRepository;
import com.enotes.exception.CategoryNotFoundException;
import com.enotes.exception.UnSupportedFileException;
import com.enotes.file.FileDetails;
import com.enotes.file.FileDetailsRepository;
import com.enotes.notes.NotesDTO.CategoryDTO;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FileDetailsUtil {

	private final FileDetailsRepository fileDetailsRepo;
    private final CategoryRepository categoryRepo;
	
	
	
	public FileDetails saveFileDetails(MultipartFile file, String uploadPath) throws IOException {
		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String extention = FilenameUtils.getExtension(originalFilename);

			List<String> allowFile = Arrays.asList("pdf", "jpg", "docx", "png");
			if (!allowFile.contains(extention)) {
				throw new UnSupportedFileException("file format is not supporte supported formate are " + allowFile);
			}

			String randomString = UUID.randomUUID().toString();
			String uploadFileName = randomString + "." + extention;

			File saveFile = new File(uploadPath);
			if (!saveFile.exists()) {
				saveFile.mkdir();
			}
			String storePath = uploadPath.concat(uploadFileName);

			// Upload file
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if (upload != 0) {
				FileDetails fileDetails = new FileDetails();
				fileDetails.setOriginalFileName(originalFilename);
				fileDetails.setDisplayFileName(getDisplayName(originalFilename));
				fileDetails.setUploadFileName(uploadFileName);
				fileDetails.setFileSize(file.getSize());
				fileDetails.setPath(storePath);

				return fileDetailsRepo.save(fileDetails);
			}

		}
		return null;
	}

	public String getDisplayName(String originalFilename) {

		// java_programming_tutorials.pdf to java_prog.pdf
		String extention = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);
		if (fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extention;
		return fileName;
	}
	
	
	public void checkCategoryExists(CategoryDTO category) {
		 Optional<Category> categoryId = categoryRepo.findById(category.getId());
		 if(!categoryId.isPresent()) {
			 throw new CategoryNotFoundException("category not found with id : "+category.getId());
		 }
		
	}
	
	
	public static String getContentType(String originalFileName) {
		String extension = FilenameUtils.getExtension(originalFileName); // java_programing.pdf

		switch (extension) {
		case "pdf":
			return "application/pdf";
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
		case "txt":
			return "text/plain";
		case "png":
			return "image/png";
		case "jpeg":
			return "image/jpeg";
		default:
			return "application/octet-stream";
		}
	}

}
