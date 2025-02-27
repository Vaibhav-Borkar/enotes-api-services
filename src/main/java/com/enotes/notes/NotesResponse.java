package com.enotes.notes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * @author : Vaibhav Borkar
 * This file generate response for pagination of notes .
 */


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotesResponse {

	private List<NotesDTO> notes;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private Long totalElements;
	
	private Integer totalPages;
	
	private Boolean isFirst;
	
	private Boolean isLast;
}
