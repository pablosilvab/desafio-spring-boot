package com.nuevospa.taskmanagement.service;

import com.nuevospa.taskmanagement.entity.TaskEntity;
import com.nuevospa.taskmanagement.exception.TaskNotFoundException;
import com.nuevospa.taskmanagement.exception.TaskOwnershipException;
import com.nuevospa.taskmanagement.mapper.TaskMapper;
import com.nuevospa.taskmanagement.repository.TaskRepository;
import org.openapitools.model.CreateTaskRequest;
import org.openapitools.model.ModelApiResponse;
import org.openapitools.model.Task;
import org.openapitools.model.UpdateTaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    public static final String DEFAULT_STATUS = "PENDIENTE";

    private final TaskRepository taskRepository;
    private final TaskStatusService taskStatusService;
    private final TaskMapper mapper;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, TaskStatusService taskStatusService, TaskMapper mapper, UserService userService) {
        this.taskRepository = taskRepository;
        this.taskStatusService = taskStatusService;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public Task createTask(CreateTaskRequest createTaskRequest) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(createTaskRequest.getName());
        taskEntity.setStatus(taskStatusService.getPendingStatus(DEFAULT_STATUS));
        taskEntity.setUser(userService.getAuthenticatedUser());
        TaskEntity saved = taskRepository.save(taskEntity);
        return mapper.toDto(saved);
    }

    @Override
    public ModelApiResponse deleteTask(Long taskId) {
        TaskEntity task = findAndValidateOwner(taskId);
        taskRepository.delete(task);
        return new ModelApiResponse().code(HttpStatus.OK.value()).message("Tarea eliminada exitosamente.");
    }

    @Override
    public Task getTaskById(Long taskId) {
        return mapper.toDto(findTaskById(taskId));
    }

    @Override
    public Task updateTask(Long taskId, UpdateTaskRequest updateTaskRequest) {
        TaskEntity task = findAndValidateOwner(taskId);
        task.setName(updateTaskRequest.getName());
        task.setUser(userService.getAuthenticatedUser());
        task.setStatus(taskStatusService.getTaskStatusById(updateTaskRequest.getStatus()));
        TaskEntity saved = taskRepository.save(task);
        return mapper.toDto(saved);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    private TaskEntity findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada."));
    }

    private TaskEntity findAndValidateOwner(Long taskId) {
        TaskEntity task = findTaskById(taskId);
        if (!task.getUser().equals(userService.getAuthenticatedUser())){
            throw new TaskOwnershipException("Operación no permitida. Usted no es propietario de esta tarea.");
        }
        return task;
    }
}
