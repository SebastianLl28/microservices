package org.app.authservice.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Alonso
 */
@Entity
@Table(name = "\"users\"")
public class UserJpaEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  private String username;
  
  private String password;
  
  public UserJpaEntity() {
  }
  
  public UserJpaEntity(String username, String password) {
    this.username = username;
    this.password = password;
  }
  
  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }

}
