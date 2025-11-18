package org.app.matriculaservice.application.dto.command;

/**
 * @author Alonso
 */
public class AssignStudentToCourseCommand {
  private Integer studentId;

  private Integer courseId;

  public AssignStudentToCourseCommand() {
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
