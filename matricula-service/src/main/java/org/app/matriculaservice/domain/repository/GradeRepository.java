package org.app.matriculaservice.domain.repository;

import java.util.List;
import org.app.matriculaservice.domain.model.Grade;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.model.valueobjects.GradeID;
import org.app.matriculaservice.domain.model.valueobjects.Score;
import org.app.matriculaservice.domain.model.valueobjects.StudentID;

public interface GradeRepository {
  void save(Grade grade);
  Grade mustGet(GradeID id);
  boolean hasApprovedAll(StudentID studentId, List<CourseID> requiredCourses, Score passingThreshold);
}
