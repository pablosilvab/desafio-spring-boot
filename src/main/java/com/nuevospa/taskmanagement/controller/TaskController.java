package com.nuevospa.taskmanagement.controller;

import com.nuevospa.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.openapitools.api.TasksApi;
import org.openapitools.model.CreateTaskRequest;
import org.openapitools.model.ModelApiResponse;
import org.openapitools.model.Task;
import org.openapitools.model.UpdateTaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController implements TasksApi {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @Override
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Override
    public ResponseEntity<Task> addTask(CreateTaskRequest createTaskRequest) {
        return new ResponseEntity<>(taskService.createTask(createTaskRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ModelApiResponse> deleteTask(Long taskId) {
        return ResponseEntity.ok(taskService.deleteTask(taskId));
    }

    @Override
    public ResponseEntity<Task> getTaskById(Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @Override
    public ResponseEntity<Task> updateTask(Long taskId, UpdateTaskRequest updateTaskRequest) {
        return ResponseEntity.ok(taskService.updateTask(taskId, updateTaskRequest));
    }
}
