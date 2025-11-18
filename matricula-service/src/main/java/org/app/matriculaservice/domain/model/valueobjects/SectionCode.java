package org.app.matriculaservice.domain.model.valueobjects;

public final class SectionCode {

  private final String value;

    public SectionCode(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("SectionCode no puede ser null o vacío");
        }
        validateFormat(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static void validateFormat(String value) {
      if (!value.matches("^[A-Z]\\d{3}$")) {
        throw new IllegalArgumentException("SectionCode tiene un formato inválido: " + value);
      }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionCode that = (SectionCode) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
