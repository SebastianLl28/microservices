package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.app.matriculaservice.domain.model.valueobjects.EnrollmentStatus;

/**
 * @author Alonso
 */
@Entity
@Table(name = "enrollment", uniqueConstraints = {
    @UniqueConstraint(name = "uk_enrollment", columnNames = {"student_id", "section_id"})
})
public class EnrollmentJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "enrollment_id")
  private Integer enrollmentId;

  @Column(name = "student_id", nullable = false, insertable = false, updatable = false)
  private Integer studentId;

  @Column(name = "section_id", nullable = false, insertable = false, updatable = false)
  private Integer sectionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false,
      foreignKey = @ForeignKey(name = "fk_student"))
  private StudentJpaEntity student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "section_id", nullable = false,
      foreignKey = @ForeignKey(name = "fk_section"))
  private SectionJpaEntity section;

  @Column(name = "enrolled_at")
  private LocalDateTime enrolledAt;

  @Column(name = "cancelled_at")
  private LocalDateTime cancelledAt;

  @Column(name = "completed_at")
  private LocalDateTime completedAt;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal cost;

  @Column(name = "paid_total", precision = 10, scale = 2)
  private BigDecimal paidTotal;

  @Column(name = "last_payment_reference", length = 100)
  private String lastPaymentReference;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EnrollmentStatus status;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @OneToOne(mappedBy = "enrollment")
  private GradeJpaEntity grade;

  public EnrollmentJpaEntity() {
  }
  
  
  public Integer getEnrollmentId() {
    return enrollmentId;
  }
  
  public void setEnrollmentId(Integer enrollmentId) {
    this.enrollmentId = enrollmentId;
  }
  
  public Integer getStudentId() {
    return studentId;
  }
  
  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
  }
  
  public Integer getSectionId() {
    return sectionId;
  }
  
  public void setSectionId(Integer sectionId) {
    this.sectionId = sectionId;
  }
  
  public StudentJpaEntity getStudent() {
    return student;
  }
  
  public void setStudent(
    StudentJpaEntity student) {
    this.student = student;
  }
  
  public SectionJpaEntity getSection() {
    return section;
  }
  
  public void setSection(
    SectionJpaEntity section) {
    this.section = section;
  }
  
  public LocalDateTime getEnrolledAt() {
    return enrolledAt;
  }
  
  public void setEnrolledAt(LocalDateTime enrolledAt) {
    this.enrolledAt = enrolledAt;
  }
  
  public LocalDateTime getCancelledAt() {
    return cancelledAt;
  }
  
  public void setCancelledAt(LocalDateTime cancelledAt) {
    this.cancelledAt = cancelledAt;
  }
  
  public LocalDateTime getCompletedAt() {
    return completedAt;
  }
  
  public void setCompletedAt(LocalDateTime completedAt) {
    this.completedAt = completedAt;
  }
  
  public BigDecimal getCost() {
    return cost;
  }
  
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }
  
  public BigDecimal getPaidTotal() {
    return paidTotal;
  }
  
  public void setPaidTotal(BigDecimal paidTotal) {
    this.paidTotal = paidTotal;
  }
  
  public String getLastPaymentReference() {
    return lastPaymentReference;
  }
  
  public void setLastPaymentReference(String lastPaymentReference) {
    this.lastPaymentReference = lastPaymentReference;
  }
  
  public EnrollmentStatus getStatus() {
    return status;
  }
  
  public void setStatus(EnrollmentStatus status) {
    this.status = status;
  }
  
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
  
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
  
  public GradeJpaEntity getGrade() {
    return grade;
  }
  
  public void setGrade(
    GradeJpaEntity grade) {
    this.grade = grade;
  }
  
  
}
