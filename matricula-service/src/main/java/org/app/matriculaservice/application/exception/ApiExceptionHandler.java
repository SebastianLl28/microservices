package org.app.matriculaservice.application.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Map;
import org.app.matriculaservice.domain.exception.AlreadyExistsException;
import org.app.matriculaservice.domain.exception.ConcurrencyException;
import org.app.matriculaservice.domain.exception.DomainException;
import org.app.matriculaservice.domain.exception.InvariantViolationException;
import org.app.matriculaservice.domain.exception.NotFoundException;
import org.app.matriculaservice.domain.exception.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Alonso
 */
@RestControllerAdvice
public class ApiExceptionHandler {
  
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
    return build(ex.getMessage(), HttpStatus.NOT_FOUND, req.getRequestURI());
  }

  @ExceptionHandler({ValidationException.class, InvariantViolationException.class})
  public ResponseEntity<ApiError> handleBadRequest(DomainException ex, HttpServletRequest req) {
    return build(ex.getMessage(), HttpStatus.BAD_REQUEST, req.getRequestURI());
  }

  @ExceptionHandler({AlreadyExistsException.class, ConcurrencyException.class})
  public ResponseEntity<ApiError> handleConflict(DomainException ex, HttpServletRequest req) {
    return build(ex.getMessage(), HttpStatus.CONFLICT, req.getRequestURI());
  }

  // Barrera contra la BD por carreras de UNIQUE, FK, etc.
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
    return build("Constraint violated", HttpStatus.CONFLICT, req.getRequestURI());
  }

  // Fallback controlado (evita 500 “vacío”)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnexpected(Exception ex, HttpServletRequest req) {
    return build("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR, req.getRequestURI());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    String msg = ex.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage())
        .findFirst()
        .orElse("Validation error");

    return ResponseEntity.badRequest().body(
        Map.of(
            "message", msg,
            "statusCode", 400,
            "path", req.getRequestURI(),
            "timestamp", Instant.now().toString()
        )
    );
  }


  private ResponseEntity<ApiError> build(String message, HttpStatus status, String path) {
    ApiError body = new ApiError(message, status.value(), path, Instant.now());
    return ResponseEntity.status(status).body(body);
  }

}
