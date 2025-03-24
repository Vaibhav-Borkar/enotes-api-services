package com.enotes.todo;

import java.util.List;

public interface TodoService {

	public Boolean saveTodo(TodoDTO todo);
	
	public TodoDTO getTodoById(Integer todoId);
	
	public List<TodoDTO> getTodoByUser();

	public Boolean updateTodo(Integer todoId,TodoDTO todo);
	
	
	
}
