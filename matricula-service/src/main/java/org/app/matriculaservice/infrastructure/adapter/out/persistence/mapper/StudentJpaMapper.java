package org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper;


import java.time.Clock;
import java.time.LocalDateTime;
import org.app.matriculaservice.domain.model.Student;
import org.app.matriculaservice.domain.model.valueobjects.DocumentNumber;
import org.app.matriculaservice.domain.model.valueobjects.Email;
import org.app.matriculaservice.domain.model.valueobjects.StudentID;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.StudentJpaEntity;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class StudentJpaMapper {

  private final Clock clock;

  public StudentJpaMapper(Clock clock) {
    this.clock = clock;
  }

  public Student toDomainStudent(StudentJpaEntity studentJpaEntity) {
    if (studentJpaEntity == null) {
      return null;
    }

    StudentID studentID = new StudentID(studentJpaEntity.getStudentId());
    Email email = new Email(studentJpaEntity.getEmail());
    DocumentNumber documentNumber = new DocumentNumber(studentJpaEntity.getDocumentNumber());

    return Student.rehydrate(studentID, studentJpaEntity.getName(), studentJpaEntity.getLastName(), documentNumber, email, studentJpaEntity.getPhoneNumber(), studentJpaEntity.getDateOfBirth(), studentJpaEntity.getAddress(), studentJpaEntity.getCreatedAt().toInstant(clock.getZone().getRules().getOffset(clock.instant())), studentJpaEntity.getActive());
  }

  public StudentJpaEntity toJpaEntity(Student student) {
    StudentJpaEntity studentJpaEntity = new StudentJpaEntity();

    if (student.getId() != null) {
      studentJpaEntity.setStudentId(student.getId().getValue());
    }

    studentJpaEntity.setName(student.getName());
    studentJpaEntity.setLastName(student.getLastName());
    studentJpaEntity.setDocumentNumber(student.getDocumentNumber().getValue());
    studentJpaEntity.setEmail(student.getEmail().getValue());
    studentJpaEntity.setPhoneNumber(student.getPhoneNumber());
    studentJpaEntity.setDateOfBirth(student.getDateOfBirth());
    studentJpaEntity.setAddress(student.getAddress());
    studentJpaEntity.setCreatedAt(LocalDateTime.now(clock));
    studentJpaEntity.setActive(student.isActive());

    return studentJpaEntity;
  }

}
