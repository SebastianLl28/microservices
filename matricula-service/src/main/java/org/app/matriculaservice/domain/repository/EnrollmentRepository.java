package org.app.matriculaservice.domain.repository;

import org.app.matriculaservice.domain.model.Enrollment;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.model.valueobjects.EnrollmentID;
import org.app.matriculaservice.domain.model.valueobjects.SectionID;
import org.app.matriculaservice.domain.model.valueobjects.StudentID;

public interface EnrollmentRepository {
  EnrollmentID nextId();
  Enrollment mustGet(EnrollmentID id);
  void save(Enrollment enrollment);
  int countBySection(SectionID sectionId);
  boolean existsByStudentAndCourseAndPeriod(StudentID studentId, CourseID courseId, String period);
}
