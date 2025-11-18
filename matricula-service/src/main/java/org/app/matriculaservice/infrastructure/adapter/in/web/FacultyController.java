package org.app.matriculaservice.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import java.util.List;
import org.app.matriculaservice.application.dto.command.CreateFacultyCommand;
import org.app.matriculaservice.application.dto.command.UpdateFacultyCommand;
import org.app.matriculaservice.application.dto.response.FacultyResponse;
import org.app.matriculaservice.application.port.in.CreateFacultyUseCase;
import org.app.matriculaservice.application.port.in.GetAllFacultyUseCase;
import org.app.matriculaservice.application.port.in.UpdateFacultyUseCase;
import org.app.matriculaservice.infrastructure.adapter.in.web.config.ApiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alonso
 */
@RestController
@RequestMapping(ApiConstants.API_PREFIX)
public class FacultyController {

  private final GetAllFacultyUseCase getAllFacultyUseCase;
  private final CreateFacultyUseCase createFacultyUseCase;
  private final UpdateFacultyUseCase updateFacultyUseCase;

  public FacultyController(GetAllFacultyUseCase getAllFacultyUseCase, CreateFacultyUseCase createFacultyUseCase, UpdateFacultyUseCase updateFacultyUseCase) {
    this.getAllFacultyUseCase = getAllFacultyUseCase;
    this.createFacultyUseCase = createFacultyUseCase;
    this.updateFacultyUseCase = updateFacultyUseCase;
  }

  @GetMapping("/faculty")
  public ResponseEntity<List<FacultyResponse>> findAll() {
    List<FacultyResponse> facultyResponseList = getAllFacultyUseCase.findAll();
    return ResponseEntity.ok(facultyResponseList);
  }

  @PostMapping("/faculty")
  public ResponseEntity<FacultyResponse> createFaculty(@RequestBody CreateFacultyCommand createFacultyCommand) {
    FacultyResponse facultyResponse = createFacultyUseCase.createFaculty(createFacultyCommand);
    return ResponseEntity.ok(facultyResponse);
  }

  @PutMapping("/faculty/{id}")
  public ResponseEntity<FacultyResponse> updateFaculty(@Valid @RequestBody UpdateFacultyCommand updateFacultyCommand, @PathVariable Integer id) {
    FacultyResponse facultyResponse = updateFacultyUseCase.updateFaculty(updateFacultyCommand, id);
    return ResponseEntity.ok(facultyResponse);
  }

}
