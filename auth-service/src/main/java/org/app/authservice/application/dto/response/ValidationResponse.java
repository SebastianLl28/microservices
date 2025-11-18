package org.app.authservice.application.dto.response;

/**
 * @author Alonso
 */
public class ValidationResponse {

  private String username;
  private boolean valid;
  
  public ValidationResponse() {
  }
  
  public ValidationResponse(String username, boolean valid) {
    this.username = username;
    this.valid = valid;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public boolean isValid() {
    return valid;
  }
  
  public void setValid(boolean valid) {
    this.valid = valid;
  }
}
