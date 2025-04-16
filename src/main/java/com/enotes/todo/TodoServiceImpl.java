package com.enotes.todo;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.enotes.exception.ResourceNotFoundException;
import com.enotes.todo.TodoDTO.StatusDTO;
import com.enotes.utils.Validation;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

	private final TodoRepository todoRepo;
	private final ModelMapper mapper;
	private final Validation validation;

	@Override
	public Boolean saveTodo(TodoDTO todo) {
		
		// Validation TODO object before further operations.
		validation.todoValidation(todo);

		if (!ObjectUtils.isEmpty(todo)) {
			Todo todoObj = mapper.map(todo, Todo.class);
			todoObj.setStatusId(todo.getStatus().getId());
			Todo save = todoRepo.save(todoObj);
			if (!ObjectUtils.isEmpty(save)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public TodoDTO getTodoById(Integer todoId) {
		Todo todo = todoRepo.findById(todoId)
				.orElseThrow(() -> new ResourceNotFoundException("todo not found for id :" + todoId));
		TodoDTO todoDto = mapper.map(todo, TodoDTO.class);
		if (!ObjectUtils.isEmpty(todoDto)) {
			setStatus(todoDto,todo);
			return todoDto;
		}
		return null;
	}

	private void setStatus(TodoDTO todoDto, Todo todo) {
		for(TodoStatus status : TodoStatus.values()) {
			if(status.getId().equals(todo.getStatusId())) {
				StatusDTO statusDTO = StatusDTO.builder()
						.id(status.getId())
						.name(status.getName())
						.build();
				todoDto.setStatus(statusDTO);
			}
		}
	}

	@Override
	public List<TodoDTO> getTodoByUser() {
		Integer userId = 1;
		List<Todo> todoList = todoRepo.findByCreatedBy(userId);
		List<TodoDTO> list = todoList.stream().map(todo -> mapper.map(todo, TodoDTO.class)).toList();
		
		// Set the status for each TodoDTO
	    for (int i = 0; i < list.size(); i++) {
	        setStatus(list.get(i), todoList.get(i));
	    }
		if (!CollectionUtils.isEmpty(list)) {
			return list;
		}
		return null;
	}

	@Override
	public Boolean updateTodo(Integer todoId,TodoDTO todo) {
		Todo todoObj = todoRepo.findById(todoId).orElseThrow(()-> new ResourceNotFoundException("todo not found for this id : "));
		todoObj.setTitle(todo.getTitle());
		todoObj.setStatusId(todo.getStatus().getId());
		if(!ObjectUtils.isEmpty(todoRepo.save(todoObj))) {
			return true;
		}
		return false;
	}

}
