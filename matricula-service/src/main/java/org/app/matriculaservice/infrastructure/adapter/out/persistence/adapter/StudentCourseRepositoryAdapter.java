package org.app.matriculaservice.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import org.app.matriculaservice.domain.exception.NotFoundException;
import org.app.matriculaservice.domain.model.StudentCourse;
import org.app.matriculaservice.domain.repository.StudentCourseRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.StudentCourseJpaEntity;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository.StudentCourseJpaRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper.StudentCourseJpaMapper;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class StudentCourseRepositoryAdapter implements StudentCourseRepository {

  private final StudentCourseJpaRepository studentCourseJpaRepository;
  private final StudentCourseJpaMapper studentCourseJpaMapper;

  public StudentCourseRepositoryAdapter(StudentCourseJpaRepository studentCourseJpaRepository,
                                        StudentCourseJpaMapper studentCourseJpaMapper) {
    this.studentCourseJpaRepository = studentCourseJpaRepository;
    this.studentCourseJpaMapper = studentCourseJpaMapper;
  }

  @Override
  public List<StudentCourse> findByStudentId(Integer studentId) {
    return studentCourseJpaRepository.findByStudentId(studentId).stream()
        .map(studentCourseJpaMapper::toDomain)
        .toList();
  }

  @Override
  public List<StudentCourse> findByCourseId(Integer courseId) {
    return studentCourseJpaRepository.findByCourseId(courseId)
        .stream()
        .map(studentCourseJpaMapper::toDomain)
        .toList();
  }

  @Override
  public StudentCourse save(StudentCourse studentCourse) {
    StudentCourseJpaEntity studentCourseJpaEntity = studentCourseJpaMapper.toJpaEntity(studentCourse);
    studentCourseJpaEntity = studentCourseJpaRepository.save(studentCourseJpaEntity);
    return studentCourseJpaMapper.toDomain(studentCourseJpaEntity);
  }

  @Override
  public StudentCourse findByStudentIdAndCourseId(Integer studentId, Integer courseId) {
    StudentCourseJpaEntity studentCourseJpaEntity = studentCourseJpaRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(
        () -> NotFoundException.ofStudentCourse(studentId, courseId)
    );
    return studentCourseJpaMapper.toDomain(studentCourseJpaEntity);
  }
}
