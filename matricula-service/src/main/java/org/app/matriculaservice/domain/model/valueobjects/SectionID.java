package org.app.matriculaservice.domain.model.valueobjects;

public final class SectionID {

  private final Long id;

  public SectionID(Long id) {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("SectionID must be a positive non-null value.");
    }
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SectionID sectionID = (SectionID) o;

    return id.equals(sectionID.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "SectionID{" + "id='" + id + '\'' + '}';
  }
}
