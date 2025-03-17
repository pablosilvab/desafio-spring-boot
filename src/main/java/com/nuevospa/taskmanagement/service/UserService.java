package com.nuevospa.taskmanagement.service;

import com.nuevospa.taskmanagement.entity.User;

public interface UserService {
    User getAuthenticatedUser();
}
