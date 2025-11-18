package org.app.matriculaservice.domain.exception;

public class InvariantViolationException extends DomainException {

  public InvariantViolationException(String message) {
    super(message);
  }

  public InvariantViolationException(String message, Throwable cause) {
    super(message, cause);
  }
}
