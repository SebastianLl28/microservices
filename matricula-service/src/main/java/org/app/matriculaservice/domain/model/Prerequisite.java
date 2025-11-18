package org.app.matriculaservice.domain.model;

import java.util.Date;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.model.valueobjects.PrerequisiteID;

public class Prerequisite {

  private final PrerequisiteID id;
  private final CourseID courseId;
  private final CourseID requiredCourseId;
  private final Date registrationDate;

  public static Prerequisite create(
      CourseID courseId,
      CourseID requiredCourseId,
      Date registrationDate
  ) {
    return new Prerequisite(
        null,
        courseId,
        requiredCourseId,
        registrationDate
    );
  }

  private Prerequisite(
      PrerequisiteID id,
      CourseID courseId,
      CourseID requiredCourseId,
      Date registrationDate
  ) {
    validateRequired(courseId, requiredCourseId, registrationDate);
    this.id = id;
    this.courseId = courseId;
    this.requiredCourseId = requiredCourseId;
    this.registrationDate = registrationDate;
  }

  private static void validateRequired(
      CourseID courseId,
      CourseID requiredCourseId,
      Date registrationDate
  ) {
    if (courseId == null) {
      throw new IllegalArgumentException("courseId no puede ser null");
    }
    if (requiredCourseId == null) {
      throw new IllegalArgumentException("requiredCourseId no puede ser null");
    }
    if (registrationDate == null) {
      throw new IllegalArgumentException("registrationDate no puede ser null");
    }
  }


  public PrerequisiteID getId() {
    return id;
  }

  public CourseID getCourseId() {
    return courseId;
  }

  public CourseID getRequiredCourseId() {
    return requiredCourseId;
  }

  public Date getRegistrationDate() {
    return registrationDate;
  }
}
