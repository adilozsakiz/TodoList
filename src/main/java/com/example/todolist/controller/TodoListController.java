package com.example.todolist.controller;

import com.example.todolist.dto.response.GenericResponse;
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
        todoListService.createTodo(request);
        return ResponseEntity.ok(new GenericResponse("data successfully created."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable(value = "id") Long id) {
        todoListService.deleteTodoById(id);
        return ResponseEntity.ok(new GenericResponse("data with given id has been deleted."));
    }

    @GetMapping(value = "/getAllTodos")
    public List<TodoResponse> getAllTodos() {
        return todoListService.getAllTodos();
    }

    @GetMapping(value = "/getTodosByDesc")
    public List<TodoResponse> getTodosByDesc(@RequestParam(value = "description", required = false  ) String description) {
        return todoListService.getTodosByDesc(description);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable(value = "id") Long id, @RequestBody TodoRequest request) {
        if(request.getDescription() == null || request.getDescription().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("description cannot be null.");
        }
        if(request.getDate()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("date cannot be null.");
        }
        todoListService.updateTodo(id, request);
        return ResponseEntity.ok(new GenericResponse("data with given id successfully updated."));
    }
}
