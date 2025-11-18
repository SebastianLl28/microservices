package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository;

import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.PrerequisiteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alonso
 */
public interface PrerequisiteJpaRepository extends JpaRepository<PrerequisiteJpaEntity, Integer> {
}
