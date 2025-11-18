package org.app.matriculaservice.application.dto.response;

/**
 * @author Alonso
 */
public class UnassignStudentToCourseCommand {
  private Integer studentId;

  private Integer courseId;

  public UnassignStudentToCourseCommand() {
  }

  public Integer getStudentId() {
    return studentId;
  }

  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
  }

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }
}
