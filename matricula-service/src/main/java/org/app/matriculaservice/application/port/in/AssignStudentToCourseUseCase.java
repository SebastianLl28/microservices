package org.app.matriculaservice.application.port.in;


import org.app.matriculaservice.application.dto.command.AssignStudentToCourseCommand;

/**
 * @author Alonso
 */
public interface AssignStudentToCourseUseCase {

  void assignStudentToCourse(AssignStudentToCourseCommand assignStudentToCourseCommand);


}
