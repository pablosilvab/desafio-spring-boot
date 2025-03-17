package com.nuevospa.taskmanagement.service;

import com.nuevospa.taskmanagement.entity.TaskEntity;
import com.nuevospa.taskmanagement.entity.TaskStatus;
import com.nuevospa.taskmanagement.entity.User;
import com.nuevospa.taskmanagement.mapper.TaskMapper;
import com.nuevospa.taskmanagement.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.model.CreateTaskRequest;
import org.openapitools.model.ModelApiResponse;
import org.openapitools.model.Task;
import org.openapitools.model.UpdateTaskRequest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskStatusService taskStatusService;

    @Mock
    private TaskMapper mapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    public static final String TASK_NAME = "Develop a REST API";
    public static final String DEFAULT_STATUS = "PENDIENTE";
    private CreateTaskRequest createTaskRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        createTaskRequest = new CreateTaskRequest().name(TASK_NAME);
    }

    @Test
    void testCreateTask() {
        TaskStatus status = new TaskStatus(1L, DEFAULT_STATUS);
        User user = User.builder().name("Juan Perez").build();
        TaskEntity savedTask = TaskEntity.builder().id(1L).name(TASK_NAME).status(status).build();
        Task taskDto = new Task().id(1L).name(TASK_NAME).status(DEFAULT_STATUS);

        when(taskStatusService.getPendingStatus(DEFAULT_STATUS)).thenReturn(status);
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(savedTask);
        when(mapper.toDto(savedTask)).thenReturn(taskDto);

        Task result = taskService.createTask(createTaskRequest);

        assertNotNull(result);
        assertEquals(TASK_NAME, result.getName());
        assertEquals(DEFAULT_STATUS, result.getStatus());
    }

    @Test
    void testGetTaskById() {
        Long taskId = 1L;
        TaskEntity task = TaskEntity.builder().id(taskId).name(TASK_NAME).build();
        Task taskDto = new Task().id(taskId).name(TASK_NAME);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(mapper.toDto(task)).thenReturn(taskDto);

        Task result = taskService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals(TASK_NAME, result.getName());
    }

    @Test
    void testUpdateTask() {
        Long taskId = 1L;
        String updatedName = "Updated Task Name";
        Long newStatusId = 2L;

        User user = User.builder().name("Juan Perez").build();
        TaskStatus newStatus = new TaskStatus(newStatusId, "BLOQUEADA");
        TaskEntity task = TaskEntity.builder().id(taskId).name(TASK_NAME).user(user).build();
        TaskEntity updatedTask = TaskEntity.builder().id(taskId).name(updatedName).status(newStatus).user(user).build();
        Task taskDto = new Task().id(taskId).name(updatedName).status("BLOQUEADA");

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest()
                .name(updatedName)
                .status(newStatusId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(taskStatusService.getTaskStatusById(newStatusId)).thenReturn(newStatus);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(updatedTask);
        when(mapper.toDto(updatedTask)).thenReturn(taskDto);

        Task result = taskService.updateTask(taskId, updateTaskRequest);

        assertNotNull(result);
        assertEquals(updatedName, result.getName());
        assertEquals("BLOQUEADA", result.getStatus());

        verify(taskRepository).save(any(TaskEntity.class));
    }

    @Test
    void testDeleteTask() {
        Long taskId = 1L;
        User user = User.builder().name("Juan Perez").build();
        TaskEntity task = TaskEntity.builder().id(taskId).name(TASK_NAME).user(user).build();

        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        ModelApiResponse response = taskService.deleteTask(taskId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());

        verify(taskRepository).delete(task);
    }



}
