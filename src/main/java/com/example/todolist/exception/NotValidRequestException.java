package com.example.todolist.exception;

public class NotValidRequestException extends RuntimeException{
    public NotValidRequestException(String msg) {
        super(msg);
    }
}
