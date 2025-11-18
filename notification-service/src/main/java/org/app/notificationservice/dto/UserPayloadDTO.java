package org.app.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Alonso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPayloadDTO {
  private String username;
  private String email;
  
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
}
