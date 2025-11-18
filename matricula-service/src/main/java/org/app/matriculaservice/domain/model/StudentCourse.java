package org.app.matriculaservice.domain.model;

import java.time.Instant;
import java.util.Objects;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.model.valueobjects.StudentCourseID;
import org.app.matriculaservice.domain.model.valueobjects.StudentID;

public class StudentCourse {

  private final StudentCourseID studentCourseId;
  private final StudentID studentId;
  private final CourseID courseId;

  private final Instant assignedAt;
  private Instant unassignedAt;
  private boolean active;

  private StudentCourse(
      StudentCourseID studentCourseId,
      StudentID studentId,
      CourseID courseId,
      Instant assignedAt,
      Instant unassignedAt,
      boolean active
  ) {
    if (studentId == null) throw new IllegalArgumentException("studentId no puede ser null");
    if (courseId == null) throw new IllegalArgumentException("courseId no puede ser null");

    this.studentCourseId = studentCourseId;
    this.studentId = studentId;
    this.courseId = courseId;
    this.assignedAt = Objects.requireNonNullElseGet(assignedAt, Instant::now);
    this.unassignedAt = unassignedAt;
    this.active = active;
  }

  // Crear una nueva asignación
  public static StudentCourse assign(StudentID studentId, CourseID courseId, Instant now) {
    return new StudentCourse(null, studentId, courseId, now, null, true);
  }

  // Reconstruir desde la base (rehidratar)
  public static StudentCourse rehydrate(
      StudentCourseID studentCourseId,
      StudentID studentId,
      CourseID courseId,
      Instant assignedAt,
      Instant unassignedAt,
      boolean active
  ) {
    return new StudentCourse(studentCourseId, studentId, courseId, assignedAt, unassignedAt, active);
  }

  public void unassign(Instant now) {
    if (!active) return;
    this.active = false;
    this.unassignedAt = now;
  }

  // Getters
  public StudentCourseID getStudentCourseId() { return studentCourseId; }
  public StudentID getStudentId() { return studentId; }
  public CourseID getCourseId() { return courseId; }
  public Instant getAssignedAt() { return assignedAt; }
  public Instant getUnassignedAt() { return unassignedAt; }
  public boolean isActive() { return active; }
}
