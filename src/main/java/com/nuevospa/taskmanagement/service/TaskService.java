package com.nuevospa.taskmanagement.service;

import org.openapitools.model.CreateTaskRequest;
import org.openapitools.model.ModelApiResponse;
import org.openapitools.model.Task;
import org.openapitools.model.UpdateTaskRequest;

import java.util.List;

public interface TaskService {
    Task createTask(CreateTaskRequest createTaskRequest);

    ModelApiResponse deleteTask(Long taskId);

    Task getTaskById(Long taskId);

    Task updateTask(Long taskId, UpdateTaskRequest updateTaskRequest);

    List<Task> getAllTasks();
}
