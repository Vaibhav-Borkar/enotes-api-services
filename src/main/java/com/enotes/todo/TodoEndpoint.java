package com.enotes.todo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.utils.SWAGGER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.enotes.utils.Constants.ROLE_USER;

@Tag(name = "Todo APIs", description = "This controller contains the APIs related to Todo module")

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

	@Operation(summary = "Save Todo", description = SWAGGER.SAVE_TODO)
	@PostMapping
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodoHandler(@RequestBody TodoDTO todo);

	@Operation(summary = "Get Todo By Id", description = SWAGGER.GET_TODO_BY_ID)
	@GetMapping("/{todoId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByIdHandler(@PathVariable Integer todoId);

	@Operation(summary = "Get Todo By User", description = SWAGGER.GET_TODO_BY_USER)
	@GetMapping
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByUser();

	@Operation(summary = "Update Todo", description = SWAGGER.UPDATE_TODO)
	@PostMapping("/{todoId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> updateTodoHandler(@PathVariable Integer todoId, @RequestBody TodoDTO todo);

}
