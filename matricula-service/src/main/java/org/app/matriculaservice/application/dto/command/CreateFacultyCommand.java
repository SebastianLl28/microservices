package org.app.matriculaservice.application.dto.command;

/**
 * @author Alonso
 */
public class CreateFacultyCommand {

  private String name;
  private String description;
  private String location;
  private String dean;

  public CreateFacultyCommand() {
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

}
