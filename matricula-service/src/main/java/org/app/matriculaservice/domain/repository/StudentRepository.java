package org.app.matriculaservice.domain.repository;

import java.util.List;
import java.util.Optional;
import org.app.matriculaservice.domain.model.Student;
import org.app.matriculaservice.domain.model.valueobjects.StudentID;

public interface StudentRepository {

  Optional<Student> findById(Integer id);

  Student mustGet(StudentID id);

  Student save(Student student);

  List<Student> findAll();

  List<Student> findAllByCourseId(Integer courseId);

}
