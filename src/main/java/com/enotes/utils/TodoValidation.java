package com.enotes.utils;

import com.enotes.todo.TodoDTO;
import com.enotes.todo.TodoDTO.StatusDTO;
import com.enotes.todo.TodoStatus;

public class TodoValidation {

	
	public void todoValidation(TodoDTO todo) {
		StatusDTO reqStatus = todo.getStatus();
		Boolean statusFound = false;
		
		for(TodoStatus status:TodoStatus.values()) {
			if(status.getId().equals(reqStatus.getId())) {
			    statusFound = true;
			}
		}
		if(!statusFound) {
			throw new IllegalArgumentException("invalid status");
		}
	}
}
