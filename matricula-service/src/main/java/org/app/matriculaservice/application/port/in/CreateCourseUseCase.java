package org.app.matriculaservice.application.port.in;

import org.app.matriculaservice.application.dto.command.CreateCourseCommand;
import org.app.matriculaservice.application.dto.response.CourseResponse;

/**
 * @author Alonso
 */
public interface CreateCourseUseCase {

  CourseResponse createCourse(CreateCourseCommand createCourseCommand);

}
