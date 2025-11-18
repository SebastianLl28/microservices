package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository;

import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.SectionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alonso
 */
public interface SectionJpaRepository extends JpaRepository<SectionJpaEntity, Integer> {
}
