package org.app.authservice.application.service;

import org.app.authservice.application.dto.command.LoginCommand;
import org.app.authservice.application.dto.command.RegisterCommand;
import org.app.authservice.application.dto.response.LoginResponse;
import org.app.authservice.application.dto.response.RegisterResponse;
import org.app.authservice.application.mapper.UserMapper;
import org.app.authservice.application.port.in.AuthUseCase;
import org.app.authservice.application.port.in.RegisterUseCase;
import org.app.authservice.application.port.in.TokenUseCase;
import org.app.authservice.application.port.out.UserEventPublisherPort;
import org.app.authservice.domain.exception.InvalidCredentialsException;
import org.app.authservice.domain.exception.UserAlreadyExistsException;
import org.app.authservice.domain.model.User;
import org.app.authservice.domain.repository.PasswordPort;
import org.app.authservice.domain.repository.TokenPort;
import org.app.authservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */
@Service
public class AuthApplicationService implements AuthUseCase, RegisterUseCase, TokenUseCase {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private PasswordPort passwordPort;
  
  @Autowired
  private TokenPort tokenPort;
  
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private UserEventPublisherPort eventPublisher;
  
  
  @Override
  public LoginResponse handle(LoginCommand command) {
    User user = userRepository.findByUsername(command.username())
      .orElseThrow(() -> new InvalidCredentialsException("Usuario o contraseña inválidos"));
    
    if (!passwordPort.matches(command.password(), user.getPassword())) {
      throw new InvalidCredentialsException("Usuario o contraseña inválidos");
    }
    
    String token = tokenPort.generateToken(user.getUsername());
    
    return new LoginResponse(token, user.getUsername());
  }
  
  @Override
  public RegisterResponse handle(RegisterCommand command) {
    userRepository.findByUsername(command.username()).ifPresent(user -> {
      throw new UserAlreadyExistsException("El username " + user.getUsername() + " ya está en uso");
    });
    
    String hashedPassword = passwordPort.hash(command.password());
    
    User newUser = User.create(
      command.username(),
      hashedPassword,
      command.fullName(),
      command.email()
    );
    
    User savedUser = userRepository.save(newUser);
    
    eventPublisher.publishUserRegistered(savedUser);
    
    return userMapper.toRegisterResponse(savedUser);
  }
  
  @Override
  public boolean validateToken(String token) {
    return tokenPort.validateToken(token);
  }
  
  @Override
  public String extractUsername(String token) {
    return tokenPort.extractUsername(token);
  }
}
