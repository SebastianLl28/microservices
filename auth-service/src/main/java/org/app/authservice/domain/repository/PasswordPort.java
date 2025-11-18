package org.app.authservice.domain.repository;

/**
 * @author Alonso
 */
public interface PasswordPort {
  String hash(String plainPassword);
  boolean matches(String plainPassword, String hashedPassword);
}
