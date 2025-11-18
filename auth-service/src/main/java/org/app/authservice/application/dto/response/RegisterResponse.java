package org.app.authservice.application.dto.response;

/**
 * @author Alonso
 */
public class RegisterResponse {
  
  private String username;
  private String message;
  
  public RegisterResponse(String username) {
    this.username = username;
    this.message = "Usuario registrado con éxito";
  }
  
  public RegisterResponse() {
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
}
