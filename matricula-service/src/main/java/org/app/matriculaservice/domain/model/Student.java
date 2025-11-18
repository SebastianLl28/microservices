package org.app.matriculaservice.domain.model;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.app.matriculaservice.domain.exception.InvariantViolationException;
import org.app.matriculaservice.domain.exception.ValidationException;
import org.app.matriculaservice.domain.model.valueobjects.DocumentNumber;
import org.app.matriculaservice.domain.model.valueobjects.Email;
import org.app.matriculaservice.domain.model.valueobjects.StudentID;

public class Student {

  private final StudentID id;
  private DocumentNumber documentNumber;
  private String name;
  private String lastName;
  private Email email;
  private String phoneNumber;
  private final LocalDate dateOfBirth;
  private String address;
  private final Instant createdAt;
  private boolean active;

  public static Student create(String name, String lastName, DocumentNumber documentNumber, Email email, String phoneNumber, LocalDate dateOfBirth, String address, Clock clock) {
    Objects.requireNonNull(clock, "clock");
    validateRequired(name, lastName, documentNumber, dateOfBirth);
    Student s = new Student(null, trim(name), trim(lastName), documentNumber, email, trimOrNull(phoneNumber), dateOfBirth, trimOrNull(address), Instant.now(clock), true);
    s.validateInvariants();
    return s;
  }

  public static Student rehydrate(StudentID id, String name, String lastName, DocumentNumber documentNumber, Email email, String phoneNumber, LocalDate dateOfBirth, String address, Instant createdAt, boolean active) {
    Objects.requireNonNull(createdAt, "createdAt");
    validateRequired(name, lastName, documentNumber, dateOfBirth);
    Student s = new Student(id, trim(name), trim(lastName), documentNumber, email, trimOrNull(phoneNumber), dateOfBirth, trimOrNull(address), createdAt, active);
    s.validateInvariants();
    return s;
  }

  private Student(StudentID id, String name, String lastName, DocumentNumber documentNumber, Email email, String phoneNumber, LocalDate dateOfBirth, String address, Instant createdAt, boolean active) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.documentNumber = documentNumber;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
    this.createdAt = createdAt;
    this.active = active;
  }

  public void rename(String newName, String newLastName) {
    if (isBlank(newName) || isBlank(newLastName)) {
      throw new ValidationException("El nombre y apellido son obligatorios.");
    }
    this.name = newName.trim();
    this.lastName = newLastName.trim();
  }

  public void changeEmail(Email newEmail) {
    this.email = newEmail;
  }

  public void changePhoneNumber(String newPhoneNumber) {
    this.phoneNumber = trimOrNull(newPhoneNumber);
  }

  public void moveToAddress(String newAddress) {
    this.address = trimOrNull(newAddress);
  }

  public void changeDocumentNumber(DocumentNumber newDocumentNumber) {
    if (newDocumentNumber == null) throw new ValidationException("documentNumber es obligatorio.");
    this.documentNumber = newDocumentNumber;
  }

  public void deactivate() {
    if (!this.active) return;
    this.active = false;
  }

  public void activate() {
    if (this.active) return;
    this.active = true;
  }

  public StudentID getId() {
    return id;
  }

  public DocumentNumber getDocumentNumber() {
    return documentNumber;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  public Email getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public String getAddress() {
    return address;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public boolean isActive() {
    return active;
  }

  public int ageOn(LocalDate onDate) {
    Objects.requireNonNull(onDate, "onDate");
    if (dateOfBirth.isAfter(onDate)) {
      throw new ValidationException("La fecha de cálculo no puede ser anterior al nacimiento.");
    }
    return Period.between(dateOfBirth, onDate).getYears();
  }

  private static void validateRequired(String name, String lastName, DocumentNumber documentNumber, LocalDate dateOfBirth) {
    List<String> missing = new ArrayList<>();
    if (isBlank(name)) missing.add("name");
    if (isBlank(lastName)) missing.add("lastName");
    if (documentNumber == null) missing.add("documentNumber");
    if (dateOfBirth == null) missing.add("dateOfBirth");
    if (!missing.isEmpty()) {
      throw new ValidationException("Campos obligatorios faltantes: " + String.join(", ", missing));
    }
  }

  private void validateInvariants() {
    if (dateOfBirth.isAfter(LocalDate.now())) {
      throw new InvariantViolationException("dateOfBirth no puede ser futura.");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Student other)) return false;
    return Objects.equals(documentNumber, other.documentNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentNumber);
  }

  @Override
  public String toString() {
    return "Student{documentNumber=" + documentNumber + ", name='" + name + "', lastName='" + lastName + "', active=" + active + "}";
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }

  private static String trim(String s) {
    if (s == null) throw new ValidationException("valor nulo no permitido");
    return s.trim();
  }

  private static String trimOrNull(String s) {
    return s == null ? null : s.trim();
  }
}
