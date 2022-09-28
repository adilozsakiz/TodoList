package com.example.todolist.controller;

import com.example.todolist.dto.request.TodoRequest;
import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.service.TodoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todolist")
public class TodoListController {

    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoRequest request) {
        if(request.getDescription() == null || request.getDescription().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("description cannot be null");
        }
        if(request.getDate()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("date cannot be null");
        }
        return new ResponseEntity<>(todoListService.createTodo(request),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TodoResponse> deleteTodo(@PathVariable Long id) {
        return new ResponseEntity<>(todoListService.deleteTodoById(id),HttpStatus.OK);
    }

    @GetMapping(value = "/getAllTodos")
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        return new ResponseEntity<>(todoListService.getAllTodos(),HttpStatus.OK);
    }

    @GetMapping(value = "/getTodosByDesc")
    public ResponseEntity<List<TodoResponse>> getTodosByDesc(@RequestParam(value = "description", required = false  ) String description) {
        return new ResponseEntity<>(todoListService.getTodosByDesc(description),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody TodoRequest request) {
        if(request.getDescription() == null || request.getDescription().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("description cannot be null.");
        }
        if(request.getDate()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("date cannot be null.");
        }
        return new ResponseEntity<>(todoListService.updateTodo(id, request),HttpStatus.OK);
    }
}
