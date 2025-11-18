package org.app.matriculaservice.domain.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

/**
 * @author Alonso
 */

public class StudentAssignedToCourseEvent extends DomainEvent {
  
  @JsonProperty("type")
  private final String type = "student.assigned_to_course";
  
  private final Integer studentId;
  private final Integer courseId;
  private final String courseName;
  private final String studentEmail;
  private final Instant assignedAt;
  
  public StudentAssignedToCourseEvent(Integer studentId, Integer courseId,
    String courseName, String studentEmail,
    Instant assignedAt) {
    super("student.assigned_to_course");
    this.studentId = studentId;
    this.courseId = courseId;
    this.courseName = courseName;
    this.studentEmail = studentEmail;
    this.assignedAt = assignedAt;
  }
  
  public String getType() { return type; }
  public Integer getStudentId() { return studentId; }
  public Integer getCourseId() { return courseId; }
  public String getCourseName() { return courseName; }
  public String getStudentEmail() { return studentEmail; }
  public Instant getAssignedAt() { return assignedAt; }
  
}
