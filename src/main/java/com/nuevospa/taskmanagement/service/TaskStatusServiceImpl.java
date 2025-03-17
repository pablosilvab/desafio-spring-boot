package com.nuevospa.taskmanagement.service;

import com.nuevospa.taskmanagement.entity.TaskStatus;
import com.nuevospa.taskmanagement.exception.TaskStatusNotFoundException;
import com.nuevospa.taskmanagement.repository.TaskStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public TaskStatus getPendingStatus(String status) {
        return taskStatusRepository.findByName(status);
    }

    @Override
    public TaskStatus getTaskStatusById(Long id) {
        return taskStatusRepository.findById(id)
                .orElseThrow(() -> new TaskStatusNotFoundException("Estado no encontrado"));
    }
}
