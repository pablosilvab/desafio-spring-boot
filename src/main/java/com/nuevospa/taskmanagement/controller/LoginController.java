package com.nuevospa.taskmanagement.controller;

import com.nuevospa.taskmanagement.security.JwtUtil;
import org.openapitools.api.LoginApi;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements LoginApi {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(new LoginResponse().jwt(token));
    }
}
