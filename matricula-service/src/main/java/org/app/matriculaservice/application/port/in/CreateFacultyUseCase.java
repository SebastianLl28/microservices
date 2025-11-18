package org.app.matriculaservice.application.port.in;

import org.app.matriculaservice.application.dto.command.CreateFacultyCommand;
import org.app.matriculaservice.application.dto.response.FacultyResponse;

/**
 * @author Alonso
 */
public interface CreateFacultyUseCase {

  FacultyResponse createFaculty(CreateFacultyCommand createFacultyCommand);

}
