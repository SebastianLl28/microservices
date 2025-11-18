package org.app.matriculaservice.application.port.in;


import org.app.matriculaservice.application.dto.command.UpdateFacultyCommand;
import org.app.matriculaservice.application.dto.response.FacultyResponse;

/**
 * @author Alonso
 */
public interface UpdateFacultyUseCase {
  FacultyResponse updateFaculty(UpdateFacultyCommand updateFacultyCommand, Integer id);
}
