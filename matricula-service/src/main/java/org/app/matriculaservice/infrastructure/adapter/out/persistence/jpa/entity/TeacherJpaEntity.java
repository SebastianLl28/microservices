package org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alonso
 */
@Entity
@Table(name = "teacher")
public class TeacherJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "teacher_id")
  private Integer teacherId;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(name = "last_name", nullable = false, length = 50)
  private String lastName;

  @Column(name = "document_number", unique = true, nullable = false, length = 20)
  private String documentNumber;

  @Column(unique = true, nullable = false, length = 100)
  private String email;

  @Column(name = "phone_number", length = 20)
  private String phoneNumber;

  @Column(length = 100)
  private String specialty;

  @Column(name = "academic_degree", length = 100)
  private String academicDegree;

  @Column(name = "registration_date")
  private LocalDateTime registrationDate;

  @Column(nullable = false)
  private Boolean active;

  @OneToMany(mappedBy = "teacher")
  private List<SectionJpaEntity> sections;

  public TeacherJpaEntity() {
  }
  
  public Integer getTeacherId() {
    return teacherId;
  }
  
  public void setTeacherId(Integer teacherId) {
    this.teacherId = teacherId;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getDocumentNumber() {
    return documentNumber;
  }
  
  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  
  public String getSpecialty() {
    return specialty;
  }
  
  public void setSpecialty(String specialty) {
    this.specialty = specialty;
  }
  
  public String getAcademicDegree() {
    return academicDegree;
  }
  
  public void setAcademicDegree(String academicDegree) {
    this.academicDegree = academicDegree;
  }
  
  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }
  
  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }
  
  public Boolean getActive() {
    return active;
  }
  
  public void setActive(Boolean active) {
    this.active = active;
  }
  
  public List<SectionJpaEntity> getSections() {
    return sections;
  }
  
  public void setSections(
    List<SectionJpaEntity> sections) {
    this.sections = sections;
  }
  
  
}
