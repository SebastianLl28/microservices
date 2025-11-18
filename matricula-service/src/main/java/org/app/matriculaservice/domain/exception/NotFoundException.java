package org.app.matriculaservice.domain.exception;

public class NotFoundException extends DomainException {
  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public static NotFoundException ofFaculty(Integer id) {
    return new NotFoundException("Faculty with id " + id + " not found");
  }

  public static NotFoundException ofCareer(Integer id) {
    return new NotFoundException("Career with id " + id + " not found");
  }

  public static NotFoundException ofStudent(Integer id) {
    return new NotFoundException("Student with id " + id + " not found");
  }

  public static NotFoundException ofCourse(Integer id) {
    return new NotFoundException("Course with id " + id + " not found");
  }

  public static NotFoundException ofStudentCourse(Integer studentId, Integer courseId) {
    return new NotFoundException("StudentCourse with student id " + studentId + " and course id " + courseId + " not found");
  }
}
