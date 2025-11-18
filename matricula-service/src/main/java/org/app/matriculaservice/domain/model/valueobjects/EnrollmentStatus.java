package org.app.matriculaservice.domain.model.valueobjects;

public enum EnrollmentStatus {

  PENDING("pending"),
  PAID("paid"),
  CANCELLED("cancelled"),
  COMPLETED("completed");

  private final String displayName;

  EnrollmentStatus(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public boolean isFinalStatus() {
    return this == CANCELLED || this == COMPLETED;
  }

  public boolean isActiveStatus() {
    return this == PENDING || this == PAID;
  }

  public boolean canTransitionTo(EnrollmentStatus newStatus) {
    if (this == CANCELLED || this == COMPLETED) {
      return false; // No transitions allowed from final states
    }
    if (this == PENDING && (newStatus == PAID || newStatus == CANCELLED)) {
      return true;
    }
    if (this == PAID && (newStatus == COMPLETED || newStatus == CANCELLED)) {
      return true;
    }
    return false;
  }
}
