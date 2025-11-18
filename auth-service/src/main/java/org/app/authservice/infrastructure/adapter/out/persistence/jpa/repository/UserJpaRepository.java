package org.app.authservice.infrastructure.adapter.out.persistence.jpa.repository;

import org.app.authservice.infrastructure.adapter.out.persistence.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alonso
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Integer> {
  
  UserJpaEntity findByUsername(String username);

}
