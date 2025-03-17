package com.nuevospa.taskmanagement.exception;

public class TaskStatusNotFoundException extends RuntimeException {
    public TaskStatusNotFoundException(String message) {
        super(message);
    }
}
