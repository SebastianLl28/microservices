package org.app.matriculaservice.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import org.app.matriculaservice.domain.exception.DomainException;
import org.app.matriculaservice.domain.model.valueobjects.EnrollmentID;
import org.app.matriculaservice.domain.model.valueobjects.EnrollmentStatus;
import org.app.matriculaservice.domain.model.valueobjects.SectionID;
import org.app.matriculaservice.domain.model.valueobjects.StudentID;

/**
 * Aggregate Root: Enrollment (Matrícula)
 * Reglas:
 *  - status inicial: PENDING
 *  - cost >= 0
 *  - Sólo se puede cancelar si no está COMPLETED
 *  - Pagos incrementales: cuando totalPago >= cost => PAID
 *  - Sólo PAID puede pasar a COMPLETED
 *  - Se permite asociar/actualizar calificación mientras no esté COMPLETED ni CANCELLED
 */
public final class Enrollment {

  private final EnrollmentID id;
  private final StudentID studentId;
  private final SectionID sectionId;

  private final Instant enrolledAt;
  private Instant cancelledAt;
  private Instant completedAt;

  private BigDecimal cost;             // Moneda simple; si prefieres VO Money, cámbialo aquí
  private BigDecimal paidTotal;        // acumulado de pagos
  private String lastPaymentReference; // opcional: última ref de pago
  private EnrollmentStatus status;     // PENDING | PAID | CANCELLED | COMPLETED

  private Instant updatedAt;

  private Enrollment(
      EnrollmentID id,
      StudentID studentId,
      SectionID sectionId,
      BigDecimal cost,
      BigDecimal paidTotal,
      EnrollmentStatus status,
      Instant enrolledAt,
      Instant cancelledAt,
      Instant completedAt,
      String lastPaymentReference,
      Instant updatedAt
  ) {
    this.id = requireNonNull(id, "EnrollmentID");
    this.studentId = requireNonNull(studentId, "StudentID");
    this.sectionId = requireNonNull(sectionId, "SectionID");
    this.cost = requireNonNegative(cost, "cost");
    this.paidTotal = nonNullOrZero(paidTotal);
    this.status = requireNonNull(status, "EnrollmentStatus");
    this.enrolledAt = enrolledAt != null ? enrolledAt : Instant.now();
    this.cancelledAt = cancelledAt;
    this.completedAt = completedAt;
    this.lastPaymentReference = lastPaymentReference;
    this.updatedAt = updatedAt != null ? updatedAt : this.enrolledAt;
  }

  public static Enrollment enroll(EnrollmentID id, StudentID studentId, SectionID sectionId, BigDecimal cost) {
//    DomainEvents.raise(new EnrollmentCreated(e.id, e.studentId, e.sectionId, e.cost));
    return new Enrollment(
        id, studentId, sectionId, cost, BigDecimal.ZERO, EnrollmentStatus.PENDING,
        null, null, null, null, null
    );
  }

  /**
   * Registra un pago (parcial o total). Cambia a PAID cuando paidTotal >= cost.
   */
  public void registerPayment(BigDecimal amount, String reference) {
    requireNotCancelledOrCompleted();
    requirePositive(amount, "payment amount");
    this.paidTotal = this.paidTotal.add(amount);
    this.lastPaymentReference = reference;
    touch();
//    DomainEvents.raise(new EnrollmentPaymentRegistered(this.id, amount, reference, this.paidTotal));

    if (this.paidTotal.compareTo(this.cost) >= 0 && this.status == EnrollmentStatus.PENDING) {
      this.status = EnrollmentStatus.PAID;
      touch();
//      DomainEvents.raise(new EnrollmentFullyPaid(this.id, this.paidTotal));
    }
  }

  /**
   * Cancela la matrícula (no permitido si COMPLETED).
   */
  public void cancel(String reason) {
    if (this.status == EnrollmentStatus.COMPLETED) {
      throw new DomainException("Cannot cancel a completed enrollment.");
    }
    if (this.status == EnrollmentStatus.CANCELLED) return;
    this.status = EnrollmentStatus.CANCELLED;
    this.cancelledAt = Instant.now();
    touch();
//    DomainEvents.raise(new EnrollmentCancelled(this.id, reason));
  }

  /**
   * Completa la matrícula (sólo si PAID).
   */
  public void complete() {
    if (this.status != EnrollmentStatus.PAID) {
      throw new DomainException("Enrollment must be PAID before completing.");
    }
    if (this.status == EnrollmentStatus.COMPLETED) return;
    this.status = EnrollmentStatus.COMPLETED;
    this.completedAt = Instant.now();
    touch();
//    DomainEvents.raise(new EnrollmentCompleted(this.id));
  }


  private void requireUpdatableGrade() {
    if (this.status == EnrollmentStatus.CANCELLED) {
      throw new DomainException("Cannot modify grade on a cancelled enrollment.");
    }
    if (this.status == EnrollmentStatus.COMPLETED) {
      throw new DomainException("Cannot modify grade on a completed enrollment.");
    }
  }

  private void requireNotCancelledOrCompleted() {
    if (this.status == EnrollmentStatus.CANCELLED) {
      throw new DomainException("Enrollment is cancelled.");
    }
    if (this.status == EnrollmentStatus.COMPLETED) {
      throw new DomainException("Enrollment is completed.");
    }
  }

  private static <T> T requireNonNull(T obj, String name) {
    if (obj == null) throw new DomainException(name + " must not be null.");
    return obj;
  }

  private static BigDecimal requireNonNegative(BigDecimal v, String name) {
    if (v == null) throw new DomainException(name + " must not be null.");
    if (v.compareTo(BigDecimal.ZERO) < 0) throw new DomainException(name + " must be >= 0.");
    return v;
  }

  private static BigDecimal nonNullOrZero(BigDecimal v) {
    return v == null ? BigDecimal.ZERO : v;
  }

  private static void requirePositive(BigDecimal v, String name) {
    if (v == null || v.compareTo(BigDecimal.ZERO) <= 0) {
      throw new DomainException(name + " must be > 0.");
    }
  }

  private void touch() {
    this.updatedAt = Instant.now();
  }

  // Getters
  public EnrollmentID id() {
    return id;
  }

  public StudentID studentId() {
    return studentId;
  }

  public SectionID sectionId() {
    return sectionId;
  }

  public Instant enrolledAt() {
    return enrolledAt;
  }

  public Instant cancelledAt() {
    return cancelledAt;
  }

  public Instant completedAt() {
    return completedAt;
  }

  public BigDecimal cost() {
    return cost;
  }

  public BigDecimal paidTotal() {
    return paidTotal;
  }

  public String lastPaymentReference() {
    return lastPaymentReference;
  }

  public EnrollmentStatus status() {
    return status;
  }

  public Instant updatedAt() {
    return updatedAt;
  }
}
