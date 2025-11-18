package org.app.matriculaservice.domain.repository;

import java.util.List;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;

public interface PrerequisiteRepository {

  List<CourseID> findRequiredCourseIds(CourseID courseId);

}
