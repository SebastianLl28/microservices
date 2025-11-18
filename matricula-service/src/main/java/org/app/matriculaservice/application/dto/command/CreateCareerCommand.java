package org.app.matriculaservice.application.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Alonso
 */
public class CreateCareerCommand {

  @NotNull(message = "Faculty ID cannot be null")
  private Integer facultyId;

  @NotBlank(message = "Name cannot be blank")
  private String name;

  private String description;

  @NotNull(message = "Semester length cannot be null")
  private int semesterLength;

  @NotBlank(message = "Degree awarded cannot be blank")
  private String degreeAwarded;

  public CreateCareerCommand() {
  }

  public Integer getFacultyId() {
    return facultyId;
  }

  public void setFacultyId(Integer facultyId) {
    this.facultyId = facultyId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getSemesterLength() {
    return semesterLength;
  }

  public void setSemesterLength(int semesterLength) {
    this.semesterLength = semesterLength;
  }

  public String getDegreeAwarded() {
    return degreeAwarded;
  }

  public void setDegreeAwarded(String degreeAwarded) {
    this.degreeAwarded = degreeAwarded;
  }

}
