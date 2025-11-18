package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository;

import java.util.List;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.StudentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Alonso
 */
public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, Integer> {

  @Query("""
    SELECT s
    FROM StudentJpaEntity s
    JOIN StudentCourseJpaEntity sc ON s.studentId = sc.studentId
    WHERE sc.courseId = :courseId AND sc.active = true
    """)
  List<StudentJpaEntity> findAllByCourseId(Integer courseId);

}
