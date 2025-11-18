package org.app.matriculaservice.domain.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

/**
 * @author Alonso
 */
public class StudentUnassignedFromCourseEvent extends DomainEvent {
  
  @JsonProperty("type")
  private final String type = "student.unassigned_from_course";
  
  private final Integer studentId;
  private final Integer courseId;
  private final String courseName;
  private final String studentEmail;
  private final Instant unassignedAt;
  
  public StudentUnassignedFromCourseEvent(Integer studentId, Integer courseId,
    String courseName, String studentEmail,
    Instant unassignedAt) {
    super("student.unassigned_from_course");
    this.studentId = studentId;
    this.courseId = courseId;
    this.courseName = courseName;
    this.studentEmail = studentEmail;
    this.unassignedAt = unassignedAt;
  }
  
  public String getType() { return type; }
  public Integer getStudentId() { return studentId; }
  public Integer getCourseId() { return courseId; }
  public String getCourseName() { return courseName; }
  public String getStudentEmail() { return studentEmail; }
  public Instant getUnassignedAt() { return unassignedAt; }
  
  @Override
  public String getEventType() {
    return type;
  }
}
