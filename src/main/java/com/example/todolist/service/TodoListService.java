package com.example.todolist.service;

import com.example.todolist.controller.TodoListController;
import com.example.todolist.dto.request.TodoRequest;
import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.entity.TodoList;
import com.example.todolist.repository.TodoListRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;


@Service
public class TodoListService {

    Logger log = LoggerFactory.getLogger(TodoListService.class);

    private TodoListRepository todoListRepository;
    private ModelMapper modelMapper;

    public TodoListService(TodoListRepository todoListRepository, ModelMapper modelMapper) {
        this.todoListRepository = todoListRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public TodoResponse createTodo(TodoRequest request) {
        var todo = modelMapper.map(request, TodoList.class);
        log.warn("New data added: {}", request);
        return modelMapper.map(todoListRepository.save(todo), TodoResponse.class);
    }

    @Transactional
    public TodoResponse deleteTodoById(Long id) {
        var todo = todoListRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        todoListRepository.deleteById(id);
        log.warn("Data is deleted: {}", todo);
        return modelMapper.map(todo, TodoResponse.class);
    }

    public TodoResponse getById(Long id) {
        var todo = todoListRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(todo, TodoResponse.class);
    }

    @Transactional
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        var todo = todoListRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        modelMapper.map(request, todo);
        log.warn("Data is updated: {} => {}", todo, request);
        return modelMapper.map(todoListRepository.save(todo), TodoResponse.class);
    }

    public List<TodoResponse> getAll(@RequestParam(value = "description", required = false) String description) {
        if (description != "" && description != null) {
            return todoListRepository.findAll().stream().filter(todo -> todo.getDescription().contains(description)).map(todo -> modelMapper.map(todo, TodoResponse.class)).toList();
        } else {
            return todoListRepository.findAll().stream().map(todo -> modelMapper.map(todo, TodoResponse.class)).toList();
        }
    }

}

