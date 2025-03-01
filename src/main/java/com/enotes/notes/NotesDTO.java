package com.enotes.notes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesDTO {

	private Integer id;

	private String title;

	private String description;

	private CategoryDTO category;
	
	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;
	
	private LocalDateTime deletedOn;
	
	private List<FileDTO> fileDetails;
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FileDTO{
		private Integer id;
		private String originalFileName;
		private String displayFileName;
	}
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CategoryDTO{
		private Integer id;
		private String name;
	}
}
