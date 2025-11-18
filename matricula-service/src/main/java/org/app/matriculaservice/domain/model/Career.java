package org.app.matriculaservice.domain.model;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import org.app.matriculaservice.domain.exception.InvariantViolationException;
import org.app.matriculaservice.domain.exception.ValidationException;
import org.app.matriculaservice.domain.model.valueobjects.CareerID;
import org.app.matriculaservice.domain.model.valueobjects.DegreeTitle;
import org.app.matriculaservice.domain.model.valueobjects.FacultyID;

public class Career {

  private final CareerID id;
  private FacultyID facultyId;
  private String name;
  private String description;
  private int semesterLength;
  private DegreeTitle degreeAwarded;
  private Instant registrationDate;
  private boolean active;

  public Career(FacultyID facultyId, String trim, String description, int semesterLength, DegreeTitle degreeAwarded, boolean active, Instant registrationDate) {
    this.id = null;
    this.facultyId = facultyId;
    this.name = trim;
    this.description = description;
    this.semesterLength = semesterLength;
    this.degreeAwarded = degreeAwarded;
    this.registrationDate = Instant.now(Clock.systemUTC());
    this.active = active;
    this.registrationDate = registrationDate;
  }

  // ---------- Fábricas ----------
  public static Career create(FacultyID facultyId, String name, String description, int semesterLength, DegreeTitle degreeAwarded, Clock clock) {
    Objects.requireNonNull(clock, "clock");
    validateRequired(facultyId, name, semesterLength, degreeAwarded);
    Career c = new Career(facultyId, trim(name), trimOrNull(description), semesterLength, degreeAwarded, true, Instant.now(clock));
    c.validateInvariants();
    return c;
  }

  public static Career rehydrate(CareerID id, FacultyID facultyId, String name, String description, int semesterLength, DegreeTitle degreeAwarded, Instant registrationDate, boolean active) {
    Objects.requireNonNull(registrationDate, "registrationDate");
    validateRequired(facultyId, name, semesterLength, degreeAwarded);
    Career c = new Career(id, facultyId, trim(name), trimOrNull(description), semesterLength, degreeAwarded, registrationDate, active);
    c.validateInvariants();
    return c;
  }

  // ---------- Ctor privado ----------


  private Career(CareerID id, FacultyID facultyId, String name, String description, int semesterLength, DegreeTitle degreeAwarded, Instant registrationDate, boolean active) {
    this.id = id;
    this.facultyId = facultyId;
    this.name = name;
    this.description = description;
    this.semesterLength = semesterLength;
    this.degreeAwarded = degreeAwarded;
    this.registrationDate = registrationDate;
    this.active = active;
  }

  // ---------- Comportamiento ----------
  public void rename(String newName) {
    if (isBlank(newName)) throw new ValidationException("El nombre de la carrera es obligatorio.");
    this.name = newName.trim();
    validateInvariants();
  }

  public void changeDescription(String newDescription) {
    this.description = trimOrNull(newDescription);
  }

  public void moveToFaculty(FacultyID newFacultyId) {
    if (newFacultyId == null) throw new ValidationException("facultyId es obligatorio.");
    this.facultyId = newFacultyId;
  }

  public void changeSemesterLength(int newLength) {
    if (newLength < 1 || newLength > 20) {
      throw new ValidationException("semesterLength debe estar entre 1 y 20.");
    }
    this.semesterLength = newLength;
  }

  public void changeDegreeAwarded(DegreeTitle newDegree) {
    if (newDegree == null) throw new ValidationException("degreeAwarded es obligatorio.");
    this.degreeAwarded = newDegree;
  }

  public void deactivate() {
    if (!active) return;
    this.active = false;
  }

  public void activate() {
    if (active) return;
    this.active = true;
  }

  // ---------- Consultas ----------
  public CareerID getId() {
    return id;
  }

  public FacultyID getFacultyId() {
    return facultyId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getSemesterLength() {
    return semesterLength;
  }

  public DegreeTitle getDegreeAwarded() {
    return degreeAwarded;
  }

  public Instant getRegistrationDate() {
    return registrationDate;
  }

  public boolean isActive() {
    return active;
  }

  // ---------- Validaciones ----------
  private static void validateRequired(FacultyID facultyId, String name, int semesterLength, DegreeTitle degree) {
    if (facultyId == null) throw new ValidationException("facultyId es obligatorio.");
    if (isBlank(name)) throw new ValidationException("name es obligatorio.");
    if (semesterLength < 1 || semesterLength > 20) {
      throw new ValidationException("semesterLength debe estar entre 1 y 20.");
    }
    if (degree == null) throw new ValidationException("degreeAwarded es obligatorio.");
  }

  private void validateInvariants() {
    if (name.length() > 120) {
      throw new InvariantViolationException("name no puede exceder 120 caracteres.");
    }
  }

  // ---------- Utils ----------
  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }

  private static String trim(String s) {
    return s.trim();
  }

  private static String trimOrNull(String s) {
    return s == null ? null : s.trim();
  }
}
