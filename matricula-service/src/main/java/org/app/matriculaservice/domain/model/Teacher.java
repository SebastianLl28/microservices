package org.app.matriculaservice.domain.model;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import org.app.matriculaservice.domain.exception.InvariantViolationException;
import org.app.matriculaservice.domain.exception.ValidationException;
import org.app.matriculaservice.domain.model.valueobjects.DegreeTitle;
import org.app.matriculaservice.domain.model.valueobjects.DocumentNumber;
import org.app.matriculaservice.domain.model.valueobjects.Email;
import org.app.matriculaservice.domain.model.valueobjects.TeacherID;

public class Teacher {
  private final TeacherID id;
  private String name;
  private String lastName;
  private DocumentNumber documentNumber;
  private Email email;
  private String phoneNumber;
  private String specialty;
  private DegreeTitle academicDegree;
  private Instant registrationDate;
  private Boolean active;

  private Teacher(
      TeacherID id,
      String name,
      String lastName,
      DocumentNumber documentNumber,
      Email email,
      String phoneNumber,
      String specialty,
      DegreeTitle academicDegree,
      boolean active,
      Instant registrationDate
  ) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.documentNumber = documentNumber;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.specialty = specialty;
    this.academicDegree = academicDegree;
    this.active = active;
    this.registrationDate = registrationDate;
  }

  public static Teacher create(
      String name,
      String lastName,
      DocumentNumber documentNumber,
      Email email,
      String phoneNumber,
      String specialty,
      DegreeTitle academicDegree,
      Clock clock
  ) {
    validateRequired(name, lastName, documentNumber, academicDegree);
    Teacher t = new Teacher(
        null,
        trim(name),
        trim(lastName),
        documentNumber,
        email,
        trimOrNull(phoneNumber),
        trimOrNull(specialty),
        academicDegree,
        true,
        Instant.now(clock)
    );
    t.validateInvariants();
    return t;
  }

  private static void validateRequired(String name, String lastName, DocumentNumber documentNumber, DegreeTitle academicDegree) {
    if (isBlank(name)) throw new ValidationException("name es obligatorio.");
    if (isBlank(lastName)) throw new ValidationException("lastName es obligatorio.");
    if (documentNumber == null) throw new ValidationException("documentNumber es obligatorio.");
    if (academicDegree == null) throw new ValidationException("academicDegree es obligatorio.");
  }

  private void validateInvariants() {
    if (name.length() > 100) {
      throw new InvariantViolationException("name no puede exceder 100 caracteres.");
    }
    if (lastName.length() > 100) {
      throw new InvariantViolationException("lastName no puede exceder 100 caracteres.");
    }
    if (phoneNumber != null && phoneNumber.trim().length() > 0 && phoneNumber.trim().length() < 6) {
      throw new InvariantViolationException("phoneNumber inválido (muy corto).");
    }
  }

  public void rename(String newName, String newLastName) {
    if (isBlank(newName) || isBlank(newLastName)) {
      throw new ValidationException("El nombre y apellido son obligatorios.");
    }
    this.name = newName.trim();
    this.lastName = newLastName.trim();
    validateInvariants();
  }

  public void changeDocumentNumber(DocumentNumber newDocumentNumber) {
    if (newDocumentNumber == null) throw new ValidationException("documentNumber es obligatorio.");
    this.documentNumber = newDocumentNumber;
  }

  public void changeEmail(Email newEmail) {
    this.email = newEmail; // puede ser null
  }

  public void changePhoneNumber(String newPhoneNumber) {
    this.phoneNumber = trimOrNull(newPhoneNumber);
  }

  public void assignSpecialty(String newSpecialty) {
    this.specialty = trimOrNull(newSpecialty);
  }

  public void changeAcademicDegree(DegreeTitle newDegree) {
    this.academicDegree = newDegree; // puede ser null
  }

  public void deactivate() { if (!active) return; this.active = false; }
  public void activate()   { if (active)  return; this.active = true;  }

  public TeacherID getId() { return id; }
  public String getName() { return name; }
  public String getLastName() { return lastName; }
  public DocumentNumber getDocumentNumber() { return documentNumber; }
  public Email getEmail() { return email; }
  public String getPhoneNumber() { return phoneNumber; }
  public String getSpecialty() { return specialty; }
  public DegreeTitle getAcademicDegree() { return academicDegree; }
  public boolean isActive() { return active; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Teacher other)) return false;
    return Objects.equals(documentNumber, other.documentNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentNumber);
  }

  @Override
  public String toString() {
    return "Teacher{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", lastName='" + lastName + '\'' +
        ", documentNumber=" + documentNumber +
        ", email=" + email +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", specialty='" + specialty + '\'' +
        ", academicDegree=" + academicDegree +
        ", registrationDate=" + registrationDate +
        ", active=" + active +
        '}';
  }

  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
  private static String trim(String s) { return s.trim(); }
  private static String trimOrNull(String s) { return s == null ? null : s.trim(); }
}
