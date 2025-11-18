package org.app.matriculaservice.application.port.in;

import java.util.List;
import org.app.matriculaservice.application.dto.response.FacultyResponse;

/**
 * @author Alonso
 */
public interface GetAllFacultyUseCase {
  List<FacultyResponse> findAll();
}
