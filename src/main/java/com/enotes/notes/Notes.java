package com.enotes.notes;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.enotes.category.BaseModel;
import com.enotes.category.Category;
import com.enotes.file.FileDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@EntityListeners(AuditingEntityListener.class)
public class Notes extends BaseModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private String description;
	
	@ManyToOne
	private Category category;
		
	@ManyToOne
	private FileDetails fileDetails;
}
