package org.app.authservice.infrastructure.adapter.out.persistence.adapter;

import java.util.Optional;
import org.app.authservice.domain.model.User;
import org.app.authservice.domain.repository.UserRepository;
import org.app.authservice.infrastructure.adapter.out.persistence.jpa.entity.UserJpaEntity;
import org.app.authservice.infrastructure.adapter.out.persistence.jpa.repository.UserJpaRepository;
import org.app.authservice.infrastructure.adapter.out.persistence.mapper.UserJpaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Alonso
 */
@Repository
public class UserRepositoryAdapter implements UserRepository {
  
  @Autowired
  private UserJpaRepository userJpaRepository;
  
  @Autowired
  private UserJpaMapper userJpaMapper;
  
  @Override
  public Optional<User> findByUsername(String username) {
    UserJpaEntity userJpaEntity = userJpaRepository.findByUsername(username);
    return userJpaEntity != null
      ? Optional.of(userJpaMapper.toDomain(userJpaEntity))
      : Optional.empty();
  }
  
  @Override
  public User save(User user) {
    UserJpaEntity userJpaEntity = userJpaMapper.toEntity(user);
    userJpaEntity = userJpaRepository.save(userJpaEntity);
    return userJpaMapper.toDomain(userJpaEntity);
  }
}
