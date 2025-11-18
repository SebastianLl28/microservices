package org.app.authservice.infrastructure.adapter.in.web;

import org.app.authservice.application.dto.command.LoginCommand;
import org.app.authservice.application.dto.command.RegisterCommand;
import org.app.authservice.application.dto.response.LoginResponse;
import org.app.authservice.application.dto.response.RegisterResponse;
import org.app.authservice.application.dto.response.ValidationResponse;
import org.app.authservice.application.port.in.AuthUseCase;
import org.app.authservice.application.port.in.RegisterUseCase;
import org.app.authservice.application.port.in.TokenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alonso
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  
  @Autowired
  private AuthUseCase authUseCase;
  
  @Autowired
  private RegisterUseCase registerUseCase;
  
  @Autowired
  private TokenUseCase tokenPort;
  
  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginCommand command) {
    return authUseCase.handle(command);
  }
  
  @PostMapping("/register")
  public RegisterResponse register(@RequestBody RegisterCommand command) {
    return registerUseCase.handle(command);
  }
  
  @GetMapping("/validateToken")
  public ResponseEntity<ValidationResponse> validateToken(@RequestParam("token") String token) {
    try {
      if (tokenPort.validateToken(token)) {
        String username = tokenPort.extractUsername(token);
        return ResponseEntity.ok(new ValidationResponse(username, true));
      } else {
        return ResponseEntity.status(401).body(new ValidationResponse(null, false));
      }
    } catch (Exception e) {
      return ResponseEntity.status(401).body(new ValidationResponse(null, false));
    }
  }
}
