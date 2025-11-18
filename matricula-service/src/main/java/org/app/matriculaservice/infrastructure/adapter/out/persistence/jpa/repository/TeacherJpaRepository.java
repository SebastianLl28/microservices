package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository;

import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.TeacherJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alonso
 */
public interface TeacherJpaRepository extends JpaRepository<TeacherJpaEntity, Integer> {
}
