package org.app.authservice.application.mapper;

import org.app.authservice.application.dto.response.RegisterResponse;
import org.app.authservice.domain.model.User;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class UserMapper {
  
  public RegisterResponse toRegisterResponse(User savedUser) {
    return new RegisterResponse(savedUser.getUsername());
  }
}
