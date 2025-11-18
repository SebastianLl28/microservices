package org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper;

import java.sql.Timestamp;
import java.time.Instant;
import org.app.matriculaservice.domain.model.Faculty;
import org.app.matriculaservice.domain.model.valueobjects.FacultyID;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.FacultyJpaEntity;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class FacultyJpaMapper {

  public Faculty toDomainFaculty(FacultyJpaEntity facultyJpaEntity) {
    FacultyID facultyID = new FacultyID(facultyJpaEntity.getFacultyId());
    Instant registrationDate = facultyJpaEntity.getRegistrationDate().toInstant(java.time.ZoneOffset.UTC);
    return Faculty.rehydrate(facultyID, facultyJpaEntity.getName(), facultyJpaEntity.getDescription(), facultyJpaEntity.getLocation(), facultyJpaEntity.getDean(), registrationDate, facultyJpaEntity.getActive());
  }

  public FacultyJpaEntity toJpaEntity(Faculty faculty) {
    FacultyJpaEntity facultyJpaEntity = new FacultyJpaEntity();

    if (faculty.getId() != null) {
      facultyJpaEntity.setFacultyId(faculty.getId().getValue());
    }

    facultyJpaEntity.setName(faculty.getName());
    facultyJpaEntity.setDescription(faculty.getDescription());
    facultyJpaEntity.setLocation(faculty.getLocation());
    facultyJpaEntity.setDean(faculty.getDean());
    facultyJpaEntity.setRegistrationDate(Timestamp.from(faculty.getRegistrationDate()).toLocalDateTime());
    facultyJpaEntity.setActive(faculty.isActive());
    return facultyJpaEntity;
  }
}
