package org.app.authservice.application.port.in;

import org.app.authservice.application.dto.command.RegisterCommand;
import org.app.authservice.application.dto.response.RegisterResponse;

/**
 * @author Alonso
 */
public interface RegisterUseCase {

  RegisterResponse handle(RegisterCommand registerCommand);
  
}
