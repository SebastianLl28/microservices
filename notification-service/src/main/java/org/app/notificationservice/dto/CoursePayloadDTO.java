package org.app.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Alonso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoursePayloadDTO {
  
  private String studentName;
  private String studentEmail;
  private String courseName;
  
  // Getters y Setters
  public String getStudentName() {
    return studentName;
  }
  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }
  public String getStudentEmail() {
    return studentEmail;
  }
  public void setStudentEmail(String studentEmail) {
    this.studentEmail = studentEmail;
  }
  public String getCourseName() {
    return courseName;
  }
  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }
}
