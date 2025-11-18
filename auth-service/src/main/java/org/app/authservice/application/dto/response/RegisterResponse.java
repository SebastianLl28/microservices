package org.app.authservice.application.dto.response;

/**
 * @author Alonso
 */
public class RegisterResponse {
  
  private String username;
  
  public RegisterResponse(String username) {
    this.username = username;
  }
  
  public RegisterResponse() {
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
}
