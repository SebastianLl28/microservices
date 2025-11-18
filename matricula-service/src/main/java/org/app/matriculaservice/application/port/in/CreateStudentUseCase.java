package org.app.matriculaservice.application.port.in;

import org.app.matriculaservice.application.dto.command.CreateStudentCommand;
import org.app.matriculaservice.application.dto.response.StudentResponse;

/**
 * @author Alonso
 */
public interface CreateStudentUseCase {

  StudentResponse createStudent(CreateStudentCommand createStudentCommand);

}
