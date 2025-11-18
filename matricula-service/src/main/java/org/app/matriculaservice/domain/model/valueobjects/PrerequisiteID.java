package org.app.matriculaservice.domain.model.valueobjects;

public final class PrerequisiteID {
  private final Integer value;

  public PrerequisiteID(Integer value) {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("PrerequisiteID must be a positive integer.");
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
    PrerequisiteID that = (PrerequisiteID) o;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "PrerequisiteID{" + "value=" + value + '}';
  }
}
