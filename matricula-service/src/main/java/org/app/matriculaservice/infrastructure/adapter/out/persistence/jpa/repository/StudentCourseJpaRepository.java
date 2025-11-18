package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.StudentCourseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alonso
 */
@Repository
public interface StudentCourseJpaRepository extends JpaRepository<StudentCourseJpaEntity, Integer> {
  List<StudentCourseJpaEntity> findByStudentId(Integer studentId);

  List<StudentCourseJpaEntity> findByCourseId(Integer courseId);

  Optional<StudentCourseJpaEntity> findByStudentIdAndCourseId(Integer studentId, Integer courseId);
}
