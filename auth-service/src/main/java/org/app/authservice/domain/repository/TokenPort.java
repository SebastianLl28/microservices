package org.app.authservice.domain.repository;

/**
 * @author Alonso
 */
public interface TokenPort {
  String generateToken(String username);
  String extractUsername(String token);
  boolean validateToken(String token);
}
