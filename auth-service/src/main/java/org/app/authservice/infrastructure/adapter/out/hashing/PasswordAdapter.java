package org.app.authservice.infrastructure.adapter.out.hashing;

import org.app.authservice.domain.repository.PasswordPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class PasswordAdapter implements PasswordPort {
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Override
  public String hash(String plainPassword) {
    return passwordEncoder.encode(plainPassword);
  }
  
  @Override
  public boolean matches(String plainPassword, String hashedPassword) {
    return passwordEncoder.matches(plainPassword, hashedPassword);
  }
}
