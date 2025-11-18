package org.app.authservice.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
  
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
