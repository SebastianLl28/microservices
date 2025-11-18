package org.app.matriculaservice.application.port.in;

import java.util.List;
import org.app.matriculaservice.application.dto.response.CourseResponse;

/**
 * @author Alonso
 */
public interface GetAllCourseUseCase {
  List<CourseResponse> findAll();
}

