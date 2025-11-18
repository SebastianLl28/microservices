package org.app.matriculaservice.application.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Alonso
 */
public class UpdateFacultyCommand {

  @NotBlank(message = "Name cannot be blank")
  private String name;

  @NotBlank(message = "Description cannot be blank")
  private String description;

  @NotBlank(message = "Location cannot be blank")
  private String location;

  @NotBlank(message = "Dean cannot be blank")
  private String dean;

  @NotNull(message = "Active status cannot be null")
  private Boolean active;

  public UpdateFacultyCommand() {
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDean() {
    return dean;
  }

  public void setDean(String dean) {
    this.dean = dean;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
