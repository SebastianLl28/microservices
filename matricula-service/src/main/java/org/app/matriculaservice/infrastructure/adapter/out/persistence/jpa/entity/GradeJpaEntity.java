package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Alonso
 */
@Entity
@Table(name = "grade")
public class GradeJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "grade_id")
  private Integer gradeId;

  @Column(name = "enrollment_id", nullable = false, insertable = false, updatable = false)
  private Integer enrollmentId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "enrollment_id", unique = true, nullable = false,
      foreignKey = @ForeignKey(name = "fk_enrollment"))
  private EnrollmentJpaEntity enrollment;

  @Column(precision = 5, scale = 2)
  private BigDecimal score;

  @Column(columnDefinition = "TEXT")
  private String observation;

  @Column(name = "score_set_at")
  private LocalDateTime scoreSetAt;

  @Column(nullable = false)
  private Boolean passed;

  @Column(nullable = false)
  private Boolean finalized;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public GradeJpaEntity() {
  }
  
  
  public Integer getGradeId() {
    return gradeId;
  }
  
  public void setGradeId(Integer gradeId) {
    this.gradeId = gradeId;
  }
  
  public Integer getEnrollmentId() {
    return enrollmentId;
  }
  
  public void setEnrollmentId(Integer enrollmentId) {
    this.enrollmentId = enrollmentId;
  }
  
  public EnrollmentJpaEntity getEnrollment() {
    return enrollment;
  }
  
  public void setEnrollment(
    EnrollmentJpaEntity enrollment) {
    this.enrollment = enrollment;
  }
  
  public BigDecimal getScore() {
    return score;
  }
  
  public void setScore(BigDecimal score) {
    this.score = score;
  }
  
  public String getObservation() {
    return observation;
  }
  
  public void setObservation(String observation) {
    this.observation = observation;
  }
  
  public LocalDateTime getScoreSetAt() {
    return scoreSetAt;
  }
  
  public void setScoreSetAt(LocalDateTime scoreSetAt) {
    this.scoreSetAt = scoreSetAt;
  }
  
  public Boolean getPassed() {
    return passed;
  }
  
  public void setPassed(Boolean passed) {
    this.passed = passed;
  }
  
  public Boolean getFinalized() {
    return finalized;
  }
  
  public void setFinalized(Boolean finalized) {
    this.finalized = finalized;
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
}
