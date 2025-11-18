package org.app.matriculaservice.domain.model.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;
import org.app.matriculaservice.domain.exception.ValidationException;

/** Código de curso: ej. CS101, MAT-205, INF300 */
public final class CourseCode {
  private static final Pattern PATTERN = Pattern.compile("^[A-Z]{2,6}-?[0-9]{2,4}$");
  private final String value;

  public CourseCode(String raw) {
    if (raw == null) throw new ValidationException("course code es obligatorio");
    String s = raw.trim().toUpperCase();
    if (!PATTERN.matcher(s).matches()) throw new ValidationException("course code inválido: " + raw);
    this.value = s;
  }

  public String getValue() { return value; }

  public static CourseCode of(String raw) { return new CourseCode(raw); }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CourseCode that)) return false;
    return value.equals(that.value);
  }
  @Override public int hashCode() { return Objects.hash(value); }
  @Override public String toString() { return value; }
}
