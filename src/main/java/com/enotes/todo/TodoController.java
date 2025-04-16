package com.enotes.todo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.utils.CommonUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/todo")
@AllArgsConstructor
public class TodoController {

	private TodoService todoService;

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveTodoHandler(@RequestBody TodoDTO todo) {
		Boolean saveTodo = todoService.saveTodo(todo);
		if (saveTodo) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED, "todo created successfully");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "todo not saved ! ");
	}

	@GetMapping("/{todoId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getTodoByIdHandler(@PathVariable Integer todoId) {
		TodoDTO todo = todoService.getTodoById(todoId);
		if (todo != null) {
			return CommonUtil.createBuildResponse(todo, HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.NOT_FOUND, "todo not found for this id");
	}
	
	
	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getTodoByUser(){
		List<TodoDTO> todoList = todoService.getTodoByUser();
		if (todoList != null) {
			return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.NO_CONTENT, "no content");
	}
	
	@PostMapping("/{todoId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateTodoHandler(@PathVariable Integer todoId,@RequestBody TodoDTO todo){
		Boolean result = todoService.updateTodo(todoId,todo);
		if(result) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "update success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "update faild ! please try agein >");
	}
	}
