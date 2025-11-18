package org.app.matriculaservice.application.mapper;

import org.app.matriculaservice.application.dto.response.FacultyResponse;
import org.app.matriculaservice.domain.model.Faculty;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class FacultyMapper {

  public FacultyResponse toResponse(Faculty faculty) {
    FacultyResponse response = new FacultyResponse();
    response.setId(faculty.getId().getValue());
    response.setName(faculty.getName());
    response.setLocation(faculty.getLocation());
    response.setActive(faculty.isActive());
    return response;
  }

//  public Faculty toDomain(CreateFacultyCommand command) {
//  }
}
