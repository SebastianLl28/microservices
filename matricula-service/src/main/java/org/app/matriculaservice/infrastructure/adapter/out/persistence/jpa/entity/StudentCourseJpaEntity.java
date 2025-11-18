package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;

/**
 * @author Alonso
 */
@Entity
@Table(name = "student_course",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_student_course", columnNames = {"student_id", "course_id"})
    })
public class StudentCourseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_course_id")
  private Integer studentCourseId;

  @Column(name = "student_id", nullable = false)
  private Integer studentId;

  @Column(name = "course_id", nullable = false)
  private Integer courseId;

  @Column(name = "assigned_at", nullable = false)
  private LocalDateTime assignedAt = LocalDateTime.now();

  @Column(name = "unassigned_at")
  private LocalDateTime unassignedAt;

  @Column(nullable = false)
  private Boolean active = true;

  public StudentCourseJpaEntity() {}
  
  public Integer getStudentCourseId() {
    return studentCourseId;
  }
  
  public void setStudentCourseId(Integer studentCourseId) {
    this.studentCourseId = studentCourseId;
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
  
  public LocalDateTime getAssignedAt() {
    return assignedAt;
  }
  
  public void setAssignedAt(LocalDateTime assignedAt) {
    this.assignedAt = assignedAt;
  }
  
  public LocalDateTime getUnassignedAt() {
    return unassignedAt;
  }
  
  public void setUnassignedAt(LocalDateTime unassignedAt) {
    this.unassignedAt = unassignedAt;
  }
  
  public Boolean getActive() {
    return active;
  }
  
  public void setActive(Boolean active) {
    this.active = active;
  }
}
