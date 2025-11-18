package org.app.authservice.infrastructure.adapter.out.persistence.mapper;

import org.app.authservice.domain.model.User;
import org.app.authservice.infrastructure.adapter.out.persistence.jpa.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class UserJpaMapper {
  
  public UserJpaEntity toEntity(User user) {
    return new UserJpaEntity(
        user.getUsername(),
        user.getPassword()
    );
  }
  
  public User toDomain(UserJpaEntity userJpaEntity) {
    return User.rehydrate(userJpaEntity.getUsername(), userJpaEntity.getPassword());
  }
}
