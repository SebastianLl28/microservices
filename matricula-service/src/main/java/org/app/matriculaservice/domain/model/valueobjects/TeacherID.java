package org.app.matriculaservice.domain.model.valueobjects;

import java.util.Objects;

public final class TeacherID {

  private final Integer value;

  public TeacherID(Integer value) {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("TeacherID must be a positive integer.");
    }
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "TeacherID{" +
        "value=" + value +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TeacherID teacherID)) return false;
    return Objects.equals(value, teacherID.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }
}
