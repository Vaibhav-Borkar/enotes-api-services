package com.enotes.todo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.enotes.utils.Constants.ROLE_USER;


@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

	@PostMapping
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodoHandler(@RequestBody TodoDTO todo);

	@GetMapping("/{todoId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByIdHandler(@PathVariable Integer todoId);

	@GetMapping
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByUser();

	@PostMapping("/{todoId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> updateTodoHandler(@PathVariable Integer todoId, @RequestBody TodoDTO todo);

}
