package com.example.employeemanagement.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

/**
 * Consistent JSON error response body.
 */
public class ApiError {
  private final Instant timestamp = Instant.now();
  private final int status;
  private final String error;
  private final String message;
  private final String path;
  private final Map<String, String> validationErrors;

  public ApiError(HttpStatus status, String message, String path, Map<String, String> validationErrors) {
    this.status = status.value();
    this.error = status.getReasonPhrase();
    this.message = message;
    this.path = path;
    this.validationErrors = validationErrors;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }

  public Map<String, String> getValidationErrors() {
    return validationErrors;
  }
}
