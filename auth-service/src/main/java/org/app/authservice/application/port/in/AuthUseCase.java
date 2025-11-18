package org.app.authservice.application.port.in;

import org.app.authservice.application.dto.command.LoginCommand;
import org.app.authservice.application.dto.response.LoginResponse;

/**
 * @author Alonso
 */
public interface AuthUseCase {
  
  LoginResponse handle(LoginCommand loginCommand);

}
