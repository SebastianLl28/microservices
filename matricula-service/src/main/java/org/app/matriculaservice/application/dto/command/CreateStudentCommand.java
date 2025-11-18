package org.app.matriculaservice.application.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Alonso
 */
public class CreateStudentCommand {

  @NotEmpty(message = "Name cannot be empty")
  private String name;

  @NotEmpty(message = "Last name cannot be empty")
  private String lastName;

  @Email
  @NotEmpty(message = "Email cannot be empty")
  private String email;

  @NotEmpty(message = "Document number cannot be empty")
  private String documentNumber;

  private String phoneNumber;

  @NotEmpty(message = "Birth date cannot be empty")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private String birthDate;

  private String address;

  public CreateStudentCommand() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
