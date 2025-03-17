package com.nuevospa.taskmanagement.service;

import com.nuevospa.taskmanagement.entity.TaskStatus;

public interface TaskStatusService {
    TaskStatus getPendingStatus(String status);

    TaskStatus getTaskStatusById(Long id);
}
