package org.app.matriculaservice.infrastructure.adapter.in.web;

import org.app.matriculaservice.application.dto.command.AssignStudentToCourseCommand;
import org.app.matriculaservice.application.dto.response.UnassignStudentToCourseCommand;
import org.app.matriculaservice.application.port.in.AssignStudentToCourseUseCase;
import org.app.matriculaservice.application.port.in.UnassignStudentToCourseUseCase;
import org.app.matriculaservice.infrastructure.adapter.in.web.config.ApiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alonso
 */
@RestController
@RequestMapping(ApiConstants.API_PREFIX)
public class StudentCourseController {

  private final AssignStudentToCourseUseCase assignStudentToCourseUseCase;
  private final UnassignStudentToCourseUseCase unassignStudentToCourseUseCase;

  public StudentCourseController(AssignStudentToCourseUseCase assignStudentToCourseUseCase, UnassignStudentToCourseUseCase unassignStudentToCourseUseCase) {
    this.assignStudentToCourseUseCase = assignStudentToCourseUseCase;
    this.unassignStudentToCourseUseCase = unassignStudentToCourseUseCase;
  }

  @PostMapping("/student-course/assign")
  public ResponseEntity<?> assignStudentToCourse(@RequestBody AssignStudentToCourseCommand assignStudentToCourseCommand) {
    assignStudentToCourseUseCase.assignStudentToCourse(assignStudentToCourseCommand);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/student-course/unassign")
  public ResponseEntity<?> unassignStudentFromCourse(@RequestBody UnassignStudentToCourseCommand unassignStudentToCourseCommand) {
    unassignStudentToCourseUseCase.unassignStudentFromCourse(unassignStudentToCourseCommand);
    return ResponseEntity.ok().build();
  }
}
