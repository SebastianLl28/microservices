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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alonso
 */

@Entity
@Table(name = "section", uniqueConstraints = {
    @UniqueConstraint(name = "uk_section_code", columnNames = {"course_id", "code"})
})
public class SectionJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "section_id")
  private Integer sectionId;

  @Column(name = "course_id", nullable = false, insertable = false, updatable = false)
  private Integer courseId;

  @Column(name = "teacher_id", nullable = false, insertable = false, updatable = false)
  private Integer teacherId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false,
      foreignKey = @ForeignKey(name = "fk_course"))
  private CourseJpaEntity course;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id", nullable = false,
      foreignKey = @ForeignKey(name = "fk_teacher"))
  private TeacherJpaEntity teacher;

  @Column(nullable = false, length = 20)
  private String code;

  @Column(name = "max_capacity", nullable = false)
  private Integer maxCapacity;

  @Column(nullable = false)
  private Boolean active;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "section")
  private List<EnrollmentJpaEntity> enrollments = new ArrayList<>();

  public SectionJpaEntity() {
  }
  
  public Integer getSectionId() {
    return sectionId;
  }
  
  public void setSectionId(Integer sectionId) {
    this.sectionId = sectionId;
  }
  
  public Integer getCourseId() {
    return courseId;
  }
  
  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }
  
  public Integer getTeacherId() {
    return teacherId;
  }
  
  public void setTeacherId(Integer teacherId) {
    this.teacherId = teacherId;
  }
  
  public CourseJpaEntity getCourse() {
    return course;
  }
  
  public void setCourse(
    CourseJpaEntity course) {
    this.course = course;
  }
  
  public TeacherJpaEntity getTeacher() {
    return teacher;
  }
  
  public void setTeacher(
    TeacherJpaEntity teacher) {
    this.teacher = teacher;
  }
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public Integer getMaxCapacity() {
    return maxCapacity;
  }
  
  public void setMaxCapacity(Integer maxCapacity) {
    this.maxCapacity = maxCapacity;
  }
  
  public Boolean getActive() {
    return active;
  }
  
  public void setActive(Boolean active) {
    this.active = active;
  }
  
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
  
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
  
  public List<EnrollmentJpaEntity> getEnrollments() {
    return enrollments;
  }
  
  public void setEnrollments(
    List<EnrollmentJpaEntity> enrollments) {
    this.enrollments = enrollments;
  }
}

