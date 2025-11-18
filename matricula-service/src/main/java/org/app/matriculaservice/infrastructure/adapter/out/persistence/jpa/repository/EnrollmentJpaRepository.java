package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository;


import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alonso
 */
public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, Integer> {
}
