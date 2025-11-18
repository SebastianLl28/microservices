package org.app.matriculaservice.domain.model;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import org.app.matriculaservice.domain.exception.InvariantViolationException;
import org.app.matriculaservice.domain.exception.ValidationException;
import org.app.matriculaservice.domain.model.valueobjects.FacultyID;

public class Faculty {

  private final FacultyID id;
  private String name;
  private String description;
  private String location;
  private String dean;
  private final Instant registrationDate;
  private Boolean active;

  public static Faculty create(String name, String description, String location, String dean, Clock clock) {
    Objects.requireNonNull(clock, "clock no puede ser null");
    validateRequired(name, location);

    Faculty faculty = new Faculty(
        null,
        trim(name),
        trimOrNull(description),
        trim(location),
        trimOrNull(dean),
        Instant.now(clock),
        true
    );
    faculty.validateInvariants();
    return faculty;
  }

  public static Faculty rehydrate(
      FacultyID id,
      String name,
      String description,
      String location,
      String dean,
      Instant registrationDate,
      boolean active
  ) {
    Objects.requireNonNull(registrationDate, "registrationDate no puede ser null");
    validateRequired(name, location);

    Faculty faculty = new Faculty(
        id,
        trim(name),
        trimOrNull(description),
        trim(location),
        trimOrNull(dean),
        registrationDate,
        active
    );
    faculty.validateInvariants();
    return faculty;
  }

  private Faculty(
      FacultyID id,
      String name,
      String description,
      String location,
      String dean,
      Instant registrationDate,
      boolean active
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.location = location;
    this.dean = dean;
    this.registrationDate = registrationDate;
    this.active = active;
  }

  public void rename(String newName) {
    if (isBlank(newName)) throw new ValidationException("El nombre de la facultad es obligatorio.");
    this.name = newName.trim();
  }

  public void relocate(String newLocation) {
    if (isBlank(newLocation)) throw new ValidationException("La ubicación no puede ser vacía.");
    this.location = newLocation.trim();
  }

  public void changeDescription(String newDescription) {
    this.description = trimOrNull(newDescription);
  }

  public void assignDean(String newDean) {
    this.dean = trimOrNull(newDean);
  }

  public void deactivate() {
    if (!this.active) return;
    this.active = false;
  }

  public void activate() {
    if (this.active) return;
    this.active = true;
  }
  public Faculty update(
      String name,
      String description,
      String location,
      String dean,
      Boolean active
  ) {
    validateRequired(name, location);
    this.name = trim(name);
    this.description = trimOrNull(description);
    this.location = trim(location);
    this.dean = trimOrNull(dean);
    this.active = active;
    validateInvariants();
    return this;
  }

  public FacultyID getId() { return id; }
  public String getName() { return name; }
  public String getDescription() { return description; }
  public String getLocation() { return location; }
  public String getDean() { return dean; }
  public Instant getRegistrationDate() { return registrationDate; }
  public boolean isActive() { return active; }

  private static void validateRequired(String name, String location) {
    if (isBlank(name)) throw new ValidationException("El nombre es obligatorio.");
    if (isBlank(location)) throw new ValidationException("La ubicación es obligatoria.");
  }

  private void validateInvariants() {
    if (name.length() > 100) {
      throw new InvariantViolationException("El nombre no puede tener más de 100 caracteres.");
    }
  }

  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
  private static String trim(String s) { return s.trim(); }
  private static String trimOrNull(String s) { return s == null ? null : s.trim(); }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Faculty other)) return false;
    return Objects.equals(name.toLowerCase(), other.name.toLowerCase());
  }

  @Override
  public int hashCode() {
    return Objects.hash(name.toLowerCase());
  }

  @Override
  public String toString() {
    return "Faculty{" +
        "name='" + name + '\'' +
        ", location='" + location + '\'' +
        ", dean='" + dean + '\'' +
        ", active=" + active +
        '}';
  }
}
