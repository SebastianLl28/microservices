package org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import org.app.matriculaservice.domain.model.Career;
import org.app.matriculaservice.domain.model.valueobjects.CareerID;
import org.app.matriculaservice.domain.model.valueobjects.DegreeTitle;
import org.app.matriculaservice.domain.model.valueobjects.FacultyID;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.CareerJpaEntity;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class CareerJpaMapper {

  public Career toDomainCareer(CareerJpaEntity careerJpaEntity) {
    CareerID careerID = new CareerID(careerJpaEntity.getCareerId());
    FacultyID facultyID = new FacultyID(careerJpaEntity.getFacultyId());
    DegreeTitle degreeTitle = new DegreeTitle(careerJpaEntity.getDegreeAwarded());
    Instant registrationDate = careerJpaEntity.getRegistrationDate().toInstant(ZoneOffset.UTC);

    return Career.rehydrate(careerID, facultyID, careerJpaEntity.getName(),
        careerJpaEntity.getDescription(), careerJpaEntity.getSemesterLength(),
        degreeTitle, registrationDate, careerJpaEntity.getActive());
  }

  public CareerJpaEntity toJpaEntity(Career career) {
    CareerJpaEntity careerJpaEntity = new CareerJpaEntity();

    if (career.getId() != null) {
      careerJpaEntity.setCareerId(career.getId().getValue());
    }

    careerJpaEntity.setFacultyId(career.getFacultyId().getValue());
    careerJpaEntity.setName(career.getName());
    careerJpaEntity.setDescription(career.getDescription());
    careerJpaEntity.setSemesterLength(career.getSemesterLength());
    careerJpaEntity.setDegreeAwarded(career.getDegreeAwarded().getValue());
    careerJpaEntity.setRegistrationDate(Timestamp.from(career.getRegistrationDate()).toLocalDateTime());
    careerJpaEntity.setActive(career.isActive());

    return careerJpaEntity;
  }
}
