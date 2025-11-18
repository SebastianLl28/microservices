package org.app.matriculaservice.domain.model.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import org.app.matriculaservice.domain.exception.DomainException;

public final class Score implements Comparable<Score> {

  private static final BigDecimal MIN = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
  private static final BigDecimal MAX = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP);

  private final BigDecimal value; // escala fija 2

  private Score(BigDecimal value) {
    BigDecimal v = require(value);
    this.value = v.setScale(2, RoundingMode.HALF_UP);
  }

  public static Score of(BigDecimal value) {
    return new Score(value);
  }

  public static Score of(double value) {
    return new Score(BigDecimal.valueOf(value));
  }

  private static BigDecimal require(BigDecimal v) {
    if (v == null) throw new DomainException("Score value must not be null.");
    if (v.compareTo(MIN) < 0 || v.compareTo(MAX) > 0) {
      throw new DomainException("Score must be between 0 and 20.");
    }
    return v;
  }

  public BigDecimal value() { return value; }

  public boolean isGreaterOrEqualThan(Score other) {
    Objects.requireNonNull(other, "other score");
    return this.value.compareTo(other.value) >= 0;
  }

  @Override public int compareTo(Score o) { return this.value.compareTo(o.value); }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Score)) return false;
    Score score = (Score) o;
    return value.compareTo(score.value) == 0;
  }

  @Override public int hashCode() { return value.hashCode(); }

  @Override public String toString() { return value.toPlainString(); }
}
