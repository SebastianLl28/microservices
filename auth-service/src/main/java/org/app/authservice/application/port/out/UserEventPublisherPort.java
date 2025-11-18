package org.app.authservice.application.port.out;

import org.app.authservice.domain.model.User;

/**
 * @author Alonso
 */
public interface UserEventPublisherPort {
  
  void publishUserRegistered(User user);
  
}
