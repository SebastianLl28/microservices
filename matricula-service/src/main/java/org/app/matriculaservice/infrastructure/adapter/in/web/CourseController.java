package org.app.matriculaservice.infrastructure.adapter.in.web;

import java.util.List;
import org.app.matriculaservice.application.dto.command.CreateCourseCommand;
import org.app.matriculaservice.application.dto.response.CourseResponse;
import org.app.matriculaservice.application.port.in.CreateCourseUseCase;
import org.app.matriculaservice.application.port.in.GetAllCourseUseCase;
import org.app.matriculaservice.infrastructure.adapter.in.web.config.ApiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alonso
 */
@RestController
@RequestMapping(ApiConstants.API_PREFIX)
@CrossOrigin(origins = "*")
public class CourseController {

  private final GetAllCourseUseCase getAllCourseUseCase;
  private final CreateCourseUseCase createCourseUseCase;

  public CourseController(GetAllCourseUseCase getAllCourseUseCase, CreateCourseUseCase createCourseUseCase) {
    this.getAllCourseUseCase = getAllCourseUseCase;
    this.createCourseUseCase = createCourseUseCase;
  }

  @GetMapping("/course")
  public ResponseEntity<List<CourseResponse>> findAll() {
    List<CourseResponse> courseResponseList = getAllCourseUseCase.findAll();
    return ResponseEntity.ok(courseResponseList);
  }

  @PostMapping("/course")
  public ResponseEntity<CourseResponse> createCourse(@RequestBody CreateCourseCommand createCourseCommand) {
    CourseResponse courseResponse = createCourseUseCase.createCourse(createCourseCommand);
    return ResponseEntity.ok(courseResponse);
  }

}
