package org.app.matriculaservice.application.port.in;


import org.app.matriculaservice.application.dto.response.UnassignStudentToCourseCommand;

/**
 * @author Alonso
 */
public interface UnassignStudentToCourseUseCase {

  void unassignStudentFromCourse(UnassignStudentToCourseCommand unassignStudentToCourseCommand);

}
