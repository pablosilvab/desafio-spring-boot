package com.nuevospa.taskmanagement.exception;

import org.openapitools.model.ModelApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({TaskNotFoundException.class, TaskStatusNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ModelApiResponse> handleNotFoundException(RuntimeException ex) {
        ModelApiResponse response = new ModelApiResponse();
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ModelApiResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        String message = String.format(
                "Error en el formato de datos: el parámetro '%s' recibió '%s'.",
                ex.getName(), Objects.toString(ex.getValue(), "null")

        );

        ModelApiResponse response = new ModelApiResponse();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(message);
        return ResponseEntity.badRequest().body(response);
    }

}
