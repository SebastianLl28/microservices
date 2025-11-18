package org.app.matriculaservice.application.port.in;

import java.util.List;
import org.app.matriculaservice.application.dto.response.StudentResponse;

/**
 * @author Alonso
 */
public interface GetAllStudentUseCase {

  List<StudentResponse> findAll();

}
