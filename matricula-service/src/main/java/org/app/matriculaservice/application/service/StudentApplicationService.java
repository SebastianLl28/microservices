package org.app.matriculaservice.application.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import org.app.matriculaservice.application.dto.command.CreateStudentCommand;
import org.app.matriculaservice.application.dto.response.StudentResponse;
import org.app.matriculaservice.application.mapper.StudentMapper;
import org.app.matriculaservice.application.port.in.CreateStudentUseCase;
import org.app.matriculaservice.application.port.in.GetAllStudentUseCase;
import org.app.matriculaservice.domain.model.Student;
import org.app.matriculaservice.domain.model.valueobjects.DocumentNumber;
import org.app.matriculaservice.domain.model.valueobjects.Email;
import org.app.matriculaservice.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */
@Service
public class StudentApplicationService implements CreateStudentUseCase, GetAllStudentUseCase {

  private final Clock clock;
  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;

  public StudentApplicationService(Clock clock, StudentRepository studentRepository, StudentMapper studentMapper) {
    this.clock = clock;
    this.studentRepository = studentRepository;
    this.studentMapper = studentMapper;
  }

  @Override
  public StudentResponse createStudent(CreateStudentCommand command) {
    DocumentNumber documentNumber = new DocumentNumber(command.getDocumentNumber());
    Email email = new Email(command.getEmail());
    LocalDate birthDate = LocalDate.parse(command.getBirthDate());
    Student student = Student.create(command.getName(), command.getLastName(), documentNumber,
        email, command.getPhoneNumber(), birthDate, command.getAddress(), clock);
    student = studentRepository.save(student);
    return studentMapper.toStudentResponse(student);
  }

  @Override
  public List<StudentResponse> findAll() {
    return studentRepository.findAll().stream().map(
        studentMapper::toStudentResponse
    ).toList();
  }
}
