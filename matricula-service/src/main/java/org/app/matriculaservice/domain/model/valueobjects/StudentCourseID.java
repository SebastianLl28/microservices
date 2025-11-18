package org.app.matriculaservice.domain.model.valueobjects;

/**
 * @author Alonso
 */
public final class StudentCourseID {
  private final Integer id;

  public StudentCourseID(Integer value) {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("StudentCourseID must be a positive non-null value.");
    }
    this.id = value;
  }

  public Integer getValue() {
    return id;
  }

  @Override
  public String toString() {
    return "StudentCourseID{" +
        "id=" + id +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof StudentCourseID studentCourseID)) return false;
    return id.equals(studentCourseID.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
