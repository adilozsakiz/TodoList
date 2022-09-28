package com.example.todolist.exception.handler;

import com.example.todolist.exception.NotValidRequestException;
import com.example.todolist.exception.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<String> handleTodoNotFoundException(TodoNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotValidRequestException.class)
    public ResponseEntity<String> handleNotValidRequestException(NotValidRequestException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
