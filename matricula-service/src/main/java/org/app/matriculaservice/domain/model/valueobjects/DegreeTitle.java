package org.app.matriculaservice.domain.model.valueobjects;

import java.util.Objects;
import org.app.matriculaservice.domain.exception.ValidationException;

public final class DegreeTitle {
  private final String value;

  public DegreeTitle(String raw) {
    if (raw == null) throw new ValidationException("degreeTitle es obligatorio");
    String s = raw.trim();
    if (s.isEmpty()) throw new ValidationException("degreeTitle vacío");
    if (s.length() > 150) throw new ValidationException("degreeTitle supera 150 caracteres");
    this.value = s;
  }

  public String getValue() { return value; }
  public static DegreeTitle of(String s) { return new DegreeTitle(s); }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DegreeTitle that)) return false;
    return value.equals(that.value);
  }
  @Override public int hashCode() { return Objects.hash(value); }
  @Override public String toString() { return value; }
}
