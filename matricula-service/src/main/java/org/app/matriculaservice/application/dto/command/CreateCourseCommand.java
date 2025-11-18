package org.app.matriculaservice.application.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCourseCommand {

  @NotNull(message = "Career ID cannot be null")
  private Integer careerId;

  @NotBlank(message = "Code cannot be blank")
  private String code;

  @NotBlank(message = "Name cannot be blank")
  private String name;

  private String description;

  @NotNull(message = "Credits cannot be null")
  private Integer credits;

  @NotNull(message = "Semester level cannot be null")
  private Integer semesterLevel;

  public CreateCourseCommand() {
  }

  public Integer getCareerId() {
    return careerId;
  }

  public void setCareerId(Integer careerId) {
    this.careerId = careerId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public Integer getCredits() {
    return credits;
  }

  public void setCredits(Integer credits) {
    this.credits = credits;
  }

  public Integer getSemesterLevel() {
    return semesterLevel;
  }

  public void setSemesterLevel(Integer semesterLevel) {
    this.semesterLevel = semesterLevel;
  }
}
