package com.enotes.file;

import com.enotes.notes.Notes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String uploadFileName;
	
	private String originalFileName;
	
	private String displayFileName;
	
	private String path;
	
	private Long fileSize;
	
//	New which i added
	@ManyToOne
	@JoinColumn(name = "notes_id") 
	private Notes notes;
}
