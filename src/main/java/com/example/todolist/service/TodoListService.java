package com.example.todolist.service;

import com.example.todolist.dto.request.TodoRequest;
import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.entity.TodoList;
import com.example.todolist.exception.NotValidRequestException;
import com.example.todolist.exception.TodoNotFoundException;
import com.example.todolist.repository.TodoListRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class TodoListService {

    Logger log = LoggerFactory.getLogger(TodoListService.class);

    private final TodoListRepository todoListRepository;
    private final ModelMapper modelMapper;

    public TodoListService(TodoListRepository todoListRepository, ModelMapper modelMapper) {
        this.todoListRepository = todoListRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public TodoResponse createTodo(TodoRequest request) {
        if(request.getDescription() == null || request.getDescription().isEmpty()){
            throw new NotValidRequestException("description can not be null.");
        }
        if(request.getDate()==null){
            throw new NotValidRequestException("date can not be null.");
        }
        var todo = modelMapper.map(request, TodoList.class);
        log.warn("New data added: {}", request);
        return modelMapper.map(todoListRepository.save(todo), TodoResponse.class);
    }

    @Transactional
    public TodoResponse deleteTodoById(Long id) {
        var todo = todoListRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found with id:"+id));
        todoListRepository.deleteById(id);
        log.warn("Data is deleted: {}", todo);
        return modelMapper.map(todo, TodoResponse.class);
    }

    @Transactional
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        var todo = todoListRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found with id:"+id));
        if(request.getDescription() == null || request.getDescription().isEmpty()){
            throw new NotValidRequestException("description can not be null.");
        }
        if(request.getDate()==null){
            throw new NotValidRequestException("date can not be null.");
        }
        log.warn("Data is updated: {} => {}", todo, request);
        modelMapper.map(request, todo);
        return modelMapper.map(todoListRepository.save(todo), TodoResponse.class);
    }

    public List<TodoResponse> getAllTodos() {
            return todoListRepository.findAll().stream().map(todo -> modelMapper.map(todo, TodoResponse.class)).toList();
        }

    public List<TodoResponse> getTodosByDesc(String description) {
        return todoListRepository.findAll().stream().filter(todo -> todo.getDescription().contains(description)).map(todo -> modelMapper.map(todo, TodoResponse.class)).toList();
    }
}

