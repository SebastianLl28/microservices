package org.app.matriculaservice.domain.model.valueobjects;

public final class EnrollmentID {

  private final Integer value;

  public EnrollmentID(Integer value) {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("EnrollmentID debe ser un entero positivo.");
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
    EnrollmentID that = (EnrollmentID) o;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "EnrollmentID{" +
        "value=" + value +
        '}';
  }

}
