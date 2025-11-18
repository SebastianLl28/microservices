package org.app.authservice.domain.model;

/**
 * @author Alonso
 */
public class User {
  
  private String username;
  
  private String password;
  
  private User() {
  }
  
  public static User create(String username, String password) {
    User user = new User();
    user.username = username;
    user.password = password;
    return user;
  }
  
  public static User rehydrate(String username, String password) {
    User user = new User();
    user.username = username;
    user.password = password;
    return user;
  }
  
  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
}
