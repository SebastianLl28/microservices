package org.app.matriculaservice.domain.model;

import java.time.Instant;
import java.util.Objects;
import org.app.matriculaservice.domain.exception.DomainException;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.model.valueobjects.SectionCode;
import org.app.matriculaservice.domain.model.valueobjects.SectionID;
import org.app.matriculaservice.domain.model.valueobjects.TeacherID;

public class Section {

  private static final int MAX_CAPACITY = 30;
  private static final int MIN_CAPACITY = 1;

  private final SectionID id;
  private final CourseID courseId;

  private TeacherID teacherId;
  private SectionCode code;
  private int maxCapacity;
  private boolean active;

  // Para auditoría ligera (opcional)
  private final Instant createdAt;
  private Instant updatedAt;

  // Constructor privado: garantiza invariantes
  private Section(
      SectionID id,
      CourseID courseId,
      TeacherID teacherId,
      SectionCode code,
      int maxCapacity,
      boolean active,
      Instant createdAt,
      Instant updatedAt
  ) {
    this.id = requireNonNull(id, "SectionID");
    this.courseId = requireNonNull(courseId, "CourseID");
    this.teacherId = requireNonNull(teacherId, "TeacherID");
    this.code = requireNonNull(code, "SectionCode");
    validateCapacity(maxCapacity);
    this.maxCapacity = maxCapacity;
    this.active = active;
    this.createdAt = createdAt != null ? createdAt : Instant.now();
    this.updatedAt = updatedAt != null ? updatedAt : this.createdAt;
  }

  // Fábrica estática: creación explícita
  public static Section create(SectionID id, CourseID courseId, TeacherID teacherId, SectionCode code, int maxCapacity) {
    // (Opcional) publicar evento de dominio
//    DomainEvents.raise(new SectionCreated(section.id, section.courseId, section.code, section.maxCapacity));
    return new Section(id, courseId, teacherId, code, maxCapacity, true, Instant.now(), null);
  }

  // Invariante de capacidad
  private static void validateCapacity(int capacity) {
    if (capacity < MIN_CAPACITY || capacity > MAX_CAPACITY) {
      throw new DomainException("Section capacity must be between " + MIN_CAPACITY + " and " + MAX_CAPACITY + ".");
    }
  }

  private static <T> T requireNonNull(T obj, String name) {
    if (obj == null) throw new DomainException(name + " must not be null.");
    return obj;
  }

  // -----------------------
  // Métodos de intención
  // -----------------------

  /** Cambiar profesor (no permite nulos ni el mismo valor innecesariamente) */
  public void assignTeacher(TeacherID newTeacherId) {
    requireActive();
    requireNonNull(newTeacherId, "TeacherID");
    if (Objects.equals(this.teacherId, newTeacherId)) return;
    TeacherID old = this.teacherId;
    this.teacherId = newTeacherId;
    touch();
//    DomainEvents.raise(new SectionTeacherAssigned(this.id, old, newTeacherId));
  }

  /** Cambiar código de sección (reglas de VO se encargan de formato) */
  public void changeCode(SectionCode newCode) {
    requireActive();
    requireNonNull(newCode, "SectionCode");
    if (Objects.equals(this.code, newCode)) return;
    SectionCode old = this.code;
    this.code = newCode;
    touch();
//    DomainEvents.raise(new SectionCodeChanged(this.id, old, newCode));
  }

  /**
   * Redimensionar capacidad.
   * Regla: 1 ≤ capacidad ≤ 30.
   * Además, no puede ser menor a los matriculados actuales (se pasa como argumento).
   */
  public void resizeCapacity(int newCapacity, int currentlyEnrolled) {
    requireActive();
    validateCapacity(newCapacity);
    if (newCapacity < currentlyEnrolled) {
      throw new DomainException("New capacity (" + newCapacity + ") cannot be less than current enrollment (" + currentlyEnrolled + ").");
    }
    if (newCapacity == this.maxCapacity) return;
    int old = this.maxCapacity;
    this.maxCapacity = newCapacity;
    touch();
//    DomainEvents.raise(new SectionCapacityChanged(this.id, old, newCapacity));
  }

  /** Validar si se puede matricular N estudiantes adicionales */
  public boolean canAccept(int seatsRequested, int currentlyEnrolled) {
    if (!active) return false;
    if (seatsRequested <= 0) return false;
    return (currentlyEnrolled + seatsRequested) <= this.maxCapacity;
  }

  /** Desactivar la sección (no admite operaciones que muten después) */
  public void deactivate() {
    if (!this.active) return;
    this.active = false;
    touch();
//    DomainEvents.raise(new SectionDeactivated(this.id));
  }

  /** Reactivar (si tu negocio lo permite) */
  public void activate() {
    if (this.active) return;
    this.active = true;
    touch();
//    DomainEvents.raise(new SectionActivated(this.id));
  }

  private void requireActive() {
    if (!this.active) {
      throw new DomainException("Section is inactive.");
    }
  }

  private void touch() {
    this.updatedAt = Instant.now();
  }

  public SectionID id() { return id; }
  public CourseID courseId() { return courseId; }
  public TeacherID teacherId() { return teacherId; }
  public SectionCode code() { return code; }
  public int maxCapacity() { return maxCapacity; }
  public boolean isActive() { return active; }
  public Instant createdAt() { return createdAt; }
  public Instant updatedAt() { return updatedAt; }
}
