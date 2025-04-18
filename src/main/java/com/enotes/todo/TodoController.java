package com.enotes.todo;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.enotes.utils.CommonUtil;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TodoController implements TodoEndpoint {

	private TodoService todoService;

	@Override
	public ResponseEntity<?> saveTodoHandler( TodoDTO todo) {
		Boolean saveTodo = todoService.saveTodo(todo);
		if (saveTodo) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.CREATED, "todo created successfully");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "todo not saved ! ");
	}

	@Override
	public ResponseEntity<?> getTodoByIdHandler( Integer todoId) {
		TodoDTO todo = todoService.getTodoById(todoId);
		if (todo != null) {
			return CommonUtil.createBuildResponse(todo, HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.NOT_FOUND, "todo not found for this id");
	}

	@Override
	public ResponseEntity<?> getTodoByUser() {
		List<TodoDTO> todoList = todoService.getTodoByUser();
		if (todoList != null) {
			return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.NO_CONTENT, "no content");
	}

	@Override
	public ResponseEntity<?> updateTodoHandler( Integer todoId,  TodoDTO todo) {
		Boolean result = todoService.updateTodo(todoId, todo);
		if (result) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK, "update success");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR,
				"update faild ! please try agein >");
	}
}
