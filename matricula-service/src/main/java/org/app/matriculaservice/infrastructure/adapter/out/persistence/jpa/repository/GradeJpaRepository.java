package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository;

import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.GradeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alonso
 */
public interface GradeJpaRepository extends JpaRepository<GradeJpaEntity, Integer> {
}
