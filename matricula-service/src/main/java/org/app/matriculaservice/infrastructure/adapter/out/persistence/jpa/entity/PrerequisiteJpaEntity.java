package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;

/**
 * @author Alonso
 */
@Entity
@Table(name = "prerequisite", uniqueConstraints = {
    @UniqueConstraint(name = "uk_prerequisite", columnNames = {"course_id", "required_course_id"})
})
public class PrerequisiteJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "prerequisite_id")
  private Integer prerequisiteId;

  @Column(name = "course_id", nullable = false, insertable = false, updatable = false)
  private Integer courseId;

  @Column(name = "required_course_id", nullable = false, insertable = false, updatable = false)
  private Integer requiredCourseId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false,
      foreignKey = @ForeignKey(name = "fk_course"))
  private CourseJpaEntity course;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "required_course_id", nullable = false,
      foreignKey = @ForeignKey(name = "fk_required_course"))
  private CourseJpaEntity requiredCourse;

  @Column(name = "registration_date")
  private LocalDateTime registrationDate;
  
  public PrerequisiteJpaEntity() {
  }
  
  public Integer getPrerequisiteId() {
    return prerequisiteId;
  }
  
  public void setPrerequisiteId(Integer prerequisiteId) {
    this.prerequisiteId = prerequisiteId;
  }
  
  public Integer getCourseId() {
    return courseId;
  }
  
  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }
  
  public Integer getRequiredCourseId() {
    return requiredCourseId;
  }
  
  public void setRequiredCourseId(Integer requiredCourseId) {
    this.requiredCourseId = requiredCourseId;
  }
  
  public CourseJpaEntity getCourse() {
    return course;
  }
  
  public void setCourse(
    CourseJpaEntity course) {
    this.course = course;
  }
  
  public CourseJpaEntity getRequiredCourse() {
    return requiredCourse;
  }
  
  public void setRequiredCourse(
    CourseJpaEntity requiredCourse) {
    this.requiredCourse = requiredCourse;
  }
  
  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }
  
  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }
}
