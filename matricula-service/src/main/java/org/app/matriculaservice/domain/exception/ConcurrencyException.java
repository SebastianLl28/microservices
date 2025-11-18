package org.app.matriculaservice.domain.exception;

public class ConcurrencyException extends RuntimeException {
  public ConcurrencyException(String message) {
    super(message);
  }
}
