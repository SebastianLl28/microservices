package org.app.matriculaservice.domain.model;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import org.app.matriculaservice.domain.exception.DomainException;
import org.app.matriculaservice.domain.model.valueobjects.EnrollmentID;
import org.app.matriculaservice.domain.model.valueobjects.GradeID;
import org.app.matriculaservice.domain.model.valueobjects.Score;

public class Grade {

  private static final Duration EDIT_WINDOW = Duration.ofDays(10);

  private final GradeID id;
  private final EnrollmentID enrollmentId;

  // VO con rango [0,20] y redondeo controlado
  private Score score;              // puede ser null hasta que se asigne por primera vez
  private String observation;

  // Momento en que se fijó por primera vez una nota (abre la ventana de edición)
  private Instant scoreSetAt;

  // Derivado del score y del umbral aprobatorio
  private boolean passed;

  // Bloqueo opcional adicional (además de la ventana de 10 días)
  private boolean finalized;

  private final Instant createdAt;
  private Instant updatedAt;

  // Dependencia inyectable para testear el tiempo
  private final Clock clock;


  private Grade(
      GradeID id,
      EnrollmentID enrollmentId,
      Score score,
      String observation,
      Instant scoreSetAt,
      boolean passed,
      boolean finalized,
      Instant createdAt,
      Instant updatedAt,
      Clock clock
  ) {
    this.id = requireNonNull(id, "GradeID");
    this.enrollmentId = requireNonNull(enrollmentId, "EnrollmentID");
    // score puede iniciar null
    this.score = score;
    this.observation = observation;
    this.scoreSetAt = scoreSetAt;
    this.passed = passed;
    this.finalized = finalized;
    this.createdAt = createdAt != null ? createdAt : Instant.now(clock != null ? clock : Clock.systemUTC());
    this.updatedAt = updatedAt != null ? updatedAt : this.createdAt;
    this.clock = clock != null ? clock : Clock.systemUTC();
  }

  public static Grade create(GradeID id, EnrollmentID enrollmentId) {
    return new Grade(id, enrollmentId, null, null, null, false, false, null, null, Clock.systemUTC());
  }

  public static Grade create(GradeID id, EnrollmentID enrollmentId, Clock clock) {
    return new Grade(id, enrollmentId, null, null, null, false, false, null, null, clock);
  }

  /**
   * Asigna la nota por primera vez o actualiza dentro de la ventana de 10 días.
   * @param newScore             VO Score (0..20)
   * @param newObservation       comentario opcional
   * @param passingThreshold     umbral aprobatorio (por ej. Score.of(11))
   */
  public void setOrUpdateScore(Score newScore, String newObservation, Score passingThreshold) {
    ensureNotFinalized();
    requireNonNull(newScore, "Score");
    requireNonNull(passingThreshold, "PassingThreshold");

    final Instant now = Instant.now(clock);

    if (this.score == null) {
      // Primera asignación: abre ventana de edición
      this.score = newScore;
      this.observation = newObservation;
      this.scoreSetAt = now;
      this.passed = newScore.isGreaterOrEqualThan(passingThreshold);
      touch(now);
      return;
    }

    ensureWithinEditWindow(now);
    // Actualización permitida dentro de la ventana
    Score old = this.score;
    this.score = newScore;
    this.observation = newObservation;
    this.passed = newScore.isGreaterOrEqualThan(passingThreshold);
    touch(now);
  }

  /** Cierra la edición, incluso si la ventana no ha vencido. */
  public void finalizeGrade() {
    ensureNotFinalized();
    if (this.score == null) {
      throw new DomainException("Cannot finalize grade without a score.");
    }
    this.finalized = true;
    touch(Instant.now(clock));
  }

  /** ¿Sigue abierta la ventana de edición de 10 días? */
  public boolean isEditWindowOpen() {
    if (this.scoreSetAt == null) return false;
    Instant now = Instant.now(clock);
    return !now.isAfter(this.scoreSetAt.plus(EDIT_WINDOW));
  }

  private void ensureWithinEditWindow(Instant now) {
    if (this.scoreSetAt == null) {
      throw new DomainException("Score was never set; call setOrUpdateScore as initial assignment.");
    }
    if (now.isAfter(this.scoreSetAt.plus(EDIT_WINDOW))) {
      throw new DomainException("Score can no longer be updated; 10-day edit window has expired.");
    }
  }

  private void ensureNotFinalized() {
    if (this.finalized) throw new DomainException("Grade is finalized and cannot be modified.");
  }

  private static <T> T requireNonNull(T v, String name) {
    if (v == null) throw new DomainException(name + " must not be null.");
    return v;
  }

  private void touch(Instant now) { this.updatedAt = now; }

  // Getters
  public GradeID id() { return id; }
  public EnrollmentID enrollmentId() { return enrollmentId; }
  public Score score() { return score; }
  public String observation() { return observation; }
  public Instant scoreSetAt() { return scoreSetAt; }
  public boolean isPassed() { return passed; }
  public boolean isFinalized() { return finalized; }
  public Instant createdAt() { return createdAt; }
  public Instant updatedAt() { return updatedAt; }
}
