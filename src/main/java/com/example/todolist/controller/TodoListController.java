package com.example.todolist.controller;

import com.example.todolist.dto.request.TodoRequest;
import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todolist")
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @PostMapping
    public TodoResponse createTodo(@RequestBody TodoRequest request){
        return  todoListService.createTodo(request);
    }

    @DeleteMapping("/{id}")
    public TodoResponse deleteTodo(@PathVariable(value="id") Long id){
        return todoListService.deleteTodoById(id);
    }

    @GetMapping(value="/getById/{id}")
    public TodoResponse getById(@PathVariable(value="id") Long id){
        return todoListService.getById(id);
    }

    @GetMapping(value="/getAll")
    public List<TodoResponse> getAll(){
        return todoListService.getAll();
    }

    @PutMapping("/{id}")
    public TodoResponse updateTodo(@PathVariable(value="id") Long id,@RequestBody TodoRequest request){
        return todoListService.updateTodo(id,request);
    }
}
