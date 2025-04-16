package com.enotes.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TodoStatus {
	
	NOT_STARTED(1,"Not started"),
	
	IN_PROGRESS(2,"In Progress"),
	
	COMPLETED(3,"Completed");
	
	private Integer id;
	
	private String name;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
