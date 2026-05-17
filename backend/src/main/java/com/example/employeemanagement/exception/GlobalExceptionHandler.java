package com.example.employeemanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler.
 *
 * Returns friendly JSON errors to the frontend.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
    ApiError body = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI(), null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
    ApiError body = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI(), null);
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    Map<String, String> errors = new LinkedHashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(err -> {
      errors.put(err.getField(), err.getDefaultMessage());
    });

    ApiError body = new ApiError(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI(), errors);
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
    // In real production apps, you may not want to expose internal messages.
    ApiError body = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), req.getRequestURI(), null);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
