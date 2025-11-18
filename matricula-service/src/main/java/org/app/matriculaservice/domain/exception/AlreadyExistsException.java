package org.app.matriculaservice.domain.exception;

public class AlreadyExistsException extends DomainException {
  public AlreadyExistsException(String message) {
    super(message);
  }
}
