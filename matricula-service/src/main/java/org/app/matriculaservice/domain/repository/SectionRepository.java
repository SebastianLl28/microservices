package org.app.matriculaservice.domain.repository;

import org.app.matriculaservice.domain.model.Section;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.model.valueobjects.SectionCode;
import org.app.matriculaservice.domain.model.valueobjects.SectionID;
import org.app.matriculaservice.domain.model.valueobjects.TeacherID;

public interface SectionRepository {

  Section findBySectionId(SectionID id);
  void save(Section section);
  void linkAcademicPeriod(SectionID id, String academicPeriod);
  boolean isUniquePerCourseAndPeriod(CourseID courseId, SectionCode code, String period);
  int countActiveByTeacherAndPeriod(TeacherID teacherId, String period);
  String getAcademicPeriod(SectionID id);

}
