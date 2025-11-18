package org.app.matriculaservice.domain.repository;

import java.util.List;
import org.app.matriculaservice.domain.model.StudentCourse;

/**
 * @author Alonso
 */
public interface StudentCourseRepository {

  List<StudentCourse> findByStudentId(Integer studentId);

  List<StudentCourse> findByCourseId(Integer courseId);

  StudentCourse save(StudentCourse studentCourse);

  StudentCourse findByStudentIdAndCourseId(Integer studentId, Integer courseId);
}
