package com.example.todolist.service;

import com.example.todolist.dto.request.TodoRequest;
import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.entity.TodoList;
import com.example.todolist.repository.TodoListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class TodoListService {

    private TodoListRepository todoListRepository;
    private ModelMapper modelMapper;

    public TodoListService(TodoListRepository todoListRepository,ModelMapper modelMapper){
        this.todoListRepository=todoListRepository;
        this.modelMapper=modelMapper;
    }

    @Transactional
    public TodoResponse createTodo(TodoRequest request) {
        var todo = modelMapper.map(request, TodoList.class);
        return modelMapper.map(todoListRepository.save(todo), TodoResponse.class);
    }

    @Transactional
    public TodoResponse deleteTodoById(Long id) {
        var todo = todoListRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
        todoListRepository.deleteById(id);
        return modelMapper.map(todo, TodoResponse.class);
    }

    public TodoResponse getById(Long id) {
        var todo = todoListRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
        return modelMapper.map(todo,TodoResponse.class);
    }

    public List<TodoResponse> getAll() {
        return todoListRepository.findAll().stream().map(todo->modelMapper.map(todo,TodoResponse.class)).toList();
    }

    @Transactional
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        var todo = todoListRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
        modelMapper.map(request,todo);
        return modelMapper.map(todoListRepository.save(todo),TodoResponse.class);
    }
}
