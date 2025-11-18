package org.app.matriculaservice.application.port.out;

import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.Student;

/**
 * @author Alonso
 */
public interface MatriculaEventPublisherPort {
  
  void publishStudentEnrolled(Student student, Course course);
  
  void publishStudentUnenrolled(Student student, Course course);

}
