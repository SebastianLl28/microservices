package org.app.matriculaservice.domain.model.valueobjects;

import java.util.Objects;

public final class CourseID {

  private final Integer id;

  public CourseID(Integer value) {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("CourseID must be a positive non-null value.");
    }
    this.id = value;
  }

  public Integer getValue() {
    return id;
  }

  @Override
  public String toString() {
    return "CourseID{" +
        "id=" + id +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof CourseID courseID)) return false;
    return Objects.equals(id, courseID.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
