package org.app.authservice.application.port.in;

/**
 * @author Alonso
 */
public interface TokenUseCase {
  
  boolean validateToken(String token);
  
  String extractUsername(String token);
  
}
