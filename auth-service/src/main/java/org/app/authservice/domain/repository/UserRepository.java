package org.app.authservice.domain.repository;

import java.util.Optional;
import org.app.authservice.domain.model.User;

/**
 * @author Alonso
 */
public interface UserRepository {
  
  Optional<User> findByUsername(String username);
  
  User save(User user);
  
}
