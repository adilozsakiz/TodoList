package com.example.todolist.exception;

public class TodoNotFoundException extends RuntimeException{
    public TodoNotFoundException(String msg) {
        super(msg);
    }

}
