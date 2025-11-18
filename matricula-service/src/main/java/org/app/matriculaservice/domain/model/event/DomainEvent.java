package org.app.matriculaservice.domain.model.event;

import java.time.Instant;

/**
 * @author Alonso
 */
public abstract class DomainEvent {
  private final String type;
  private final Instant occurredOn = Instant.now();
  
  protected DomainEvent(String type) { this.type = type; }
  
  public String getType() { return type; }
  public Instant getOccurredOn() { return occurredOn; }
  public String getEventType() {
    return type;
  }
}
