package org.app.matriculaservice.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import org.app.matriculaservice.domain.model.Student;
import org.app.matriculaservice.domain.model.valueobjects.StudentID;
import org.app.matriculaservice.domain.repository.StudentRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.StudentJpaEntity;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository.StudentJpaRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper.StudentJpaMapper;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class StudentRepositoryAdapter implements StudentRepository {

  private final StudentJpaRepository studentJpaRepository;
  private final StudentJpaMapper studentJpaMapper;

  public StudentRepositoryAdapter(StudentJpaRepository studentJpaRepository, StudentJpaMapper studentJpaMapper) {
    this.studentJpaRepository = studentJpaRepository;
    this.studentJpaMapper = studentJpaMapper;
  }


  @Override
  public Optional<Student> findById(Integer id) {
    return studentJpaRepository.findById(id).map(studentJpaMapper::toDomainStudent);
  }

  @Override
  public Student mustGet(StudentID id) {
    return findById(id.getValue()).orElseThrow(() -> new RuntimeException("Student not found with id: " + id.getValue()));
  }

  @Override
  public Student save(Student student) {
    StudentJpaEntity studentJpaEntity = studentJpaMapper.toJpaEntity(student);
    studentJpaEntity = studentJpaRepository.save(studentJpaEntity);
    return studentJpaMapper.toDomainStudent(studentJpaEntity);
  }

  @Override
  public List<Student> findAll() {
    return studentJpaRepository.findAll().stream().map(studentJpaMapper::toDomainStudent).toList();
  }

  @Override
  public List<Student> findAllByCourseId(Integer courseId) {
    return studentJpaRepository.findAllByCourseId(courseId).stream().map(studentJpaMapper::toDomainStudent).toList();
  }
}
