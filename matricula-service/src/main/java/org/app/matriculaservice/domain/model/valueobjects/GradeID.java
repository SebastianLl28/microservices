package org.app.matriculaservice.domain.model.valueobjects;

public final class GradeID {

  private final Integer value;

  public GradeID(Integer value) {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("GradeID debe ser un entero positivo.");
    }
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GradeID gradeID = (GradeID) o;
    return value.equals(gradeID.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "GradeID{" +
        "value=" + value +
        '}';
  }

}
