package com.nuevospa.taskmanagement.service;

import com.nuevospa.taskmanagement.entity.User;
import com.nuevospa.taskmanagement.exception.UserNotFoundException;
import com.nuevospa.taskmanagement.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        }

        throw new UserNotFoundException("Usuario no autenticado");
    }
}
