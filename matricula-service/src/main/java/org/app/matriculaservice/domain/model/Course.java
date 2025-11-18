package org.app.matriculaservice.domain.model;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import org.app.matriculaservice.domain.exception.InvariantViolationException;
import org.app.matriculaservice.domain.exception.ValidationException;
import org.app.matriculaservice.domain.model.valueobjects.CareerID;
import org.app.matriculaservice.domain.model.valueobjects.CourseCode;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.model.valueobjects.Credits;
import org.app.matriculaservice.domain.model.valueobjects.SemesterLevel;

public class Course {

  private final CourseID id;
  private CareerID careerId;
  private CourseCode code;
  private String name;
  private String description;
  private Credits credits ;
  private SemesterLevel semesterLevel;
  private final Instant registrationDate;
  private boolean active;

  // ---------- Fábricas ----------
  public static Course create(
      CareerID careerId,
      CourseCode code,
      String name,
      String description,
      Credits credits,
      SemesterLevel semesterLevel,
      Clock clock
  ) {
    Objects.requireNonNull(clock, "clock");
    validateRequired(careerId, code, name, credits, semesterLevel);
    Course c = new Course(
        null,
        careerId,
        code,
        trim(name),
        trimOrNull(description),
        credits,
        semesterLevel,
        Instant.now(clock),
        true
    );
    c.validateInvariants();
    return c;
  }

  public static Course rehydrate(
      CourseID id,
      CareerID careerId,
      CourseCode code,
      String name,
      String description,
      Credits credits,
      SemesterLevel semesterLevel,
      Instant registrationDate,
      boolean active
  ) {
    Objects.requireNonNull(registrationDate, "registrationDate");
    validateRequired(careerId, code, name, credits, semesterLevel);
    Course c = new Course(
        id,
        careerId,
        code,
        trim(name),
        trimOrNull(description),
        credits,
        semesterLevel,
        registrationDate,
        active
    );
    c.validateInvariants();
    return c;
  }

  // ---------- Ctor privado ----------
  private Course(
      CourseID id,
      CareerID careerId,
      CourseCode code,
      String name,
      String description,
      Credits credits,
      SemesterLevel semesterLevel,
      Instant registrationDate,
      boolean active
  ) {
    this.id = id;
    this.careerId = careerId;
    this.code = code;
    this.name = name;
    this.description = description;
    this.credits = credits;
    this.semesterLevel = semesterLevel;
    this.registrationDate = registrationDate;
    this.active = active;
  }

  // ---------- Comportamiento ----------
  public void rename(String newName) {
    if (isBlank(newName)) throw new ValidationException("El nombre del curso es obligatorio.");
    this.name = newName.trim();
    validateInvariants();
  }

  public void recode(CourseCode newCode) {
    if (newCode == null) throw new ValidationException("El código del curso es obligatorio.");
    this.code = newCode;
  }

  public void changeDescription(String newDescription) {
    this.description = trimOrNull(newDescription);
  }

  public void changeCredits(Credits newCredits) {
    if (newCredits == null) throw new ValidationException("Los créditos son obligatorios.");
    this.credits = newCredits;
  }

  public void changeSemesterLevel(SemesterLevel newLevel) {
    if (newLevel == null) throw new ValidationException("El nivel de semestre es obligatorio.");
    this.semesterLevel = newLevel;
  }

  public void moveToCareer(CareerID newCareerId) {
    if (newCareerId == null) throw new ValidationException("careerId es obligatorio.");
    this.careerId = newCareerId;
  }

  public void deactivate() { if (!active) return; this.active = false; }
  public void activate()   { if (active)  return; this.active = true;  }

  // ---------- Consultas ----------
  public CourseID getId() { return id; }
  public CareerID getCareerId() { return careerId; }
  public CourseCode getCode() { return code; }
  public String getName() { return name; }
  public String getDescription() { return description; }
  public Credits getCredits() { return credits; }
  public SemesterLevel getSemesterLevel() { return semesterLevel; }
  public Instant getRegistrationDate() { return registrationDate; }
  public boolean isActive() { return active; }

  // ---------- Validaciones ----------
  private static void validateRequired(CareerID careerId, CourseCode code, String name, Credits credits, SemesterLevel level) {
//    if (careerId == null) throw new ValidationException("careerId es obligatorio.");
//    if (code == null)     throw new ValidationException("code es obligatorio.");
//    if (isBlank(name))    throw new ValidationException("name es obligatorio.");
//    if (credits == null)  throw new ValidationException("credits es obligatorio.");
//    if (level == null)    throw new ValidationException("semesterLevel es obligatorio.");
  }

  private void validateInvariants() {
    if (name.length() > 120) {
      throw new InvariantViolationException("name no puede exceder 120 caracteres.");
    }
  }

  // ---------- Utils ----------
  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
  private static String trim(String s) { return s.trim(); }
  private static String trimOrNull(String s) { return s == null ? null : s.trim(); }
}
