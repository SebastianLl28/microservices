package org.app.authservice.domain.model;

/**
 * @author Alonso
 */
public class User {
  
  private String username;
  
  private String password;
  
  private String fullName;
  
  private String email;
  
  private User() {
  }
  
  public static User create(String username, String password, String fullName, String email) {
    User user = new User();
    user.username = username;
    user.password = password;
    user.fullName = fullName;
    user.email = email;
    return user;
  }
  
  public static User rehydrate(String username, String password, String fullName, String email) {
    User user = new User();
    user.username = username;
    user.password = password;
    user.fullName = fullName;
    user.email = email;
    return user;
  }
  
  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public String getFullName() {
    return fullName;
  }
  
  public String getEmail() {
    return email;
  }
}
