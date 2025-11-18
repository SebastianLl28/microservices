package org.app.matriculaservice.domain.model.event;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
  private final List<DomainEvent> uncommitted = new ArrayList<>();
  protected void raise(DomainEvent e) { uncommitted.add(e); }
  public List<DomainEvent> pullDomainEvents() {
    List<DomainEvent> copy = List.copyOf(uncommitted);
    uncommitted.clear(); return copy;
  }
}
