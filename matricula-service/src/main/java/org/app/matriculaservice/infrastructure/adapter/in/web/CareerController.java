package org.app.matriculaservice.infrastructure.adapter.in.web;


import java.util.List;
import org.app.matriculaservice.application.dto.command.CreateCareerCommand;
import org.app.matriculaservice.application.dto.response.CareerResponse;
import org.app.matriculaservice.application.port.in.CreateCareerUseCase;
import org.app.matriculaservice.application.port.in.GetAllCareerUseCase;
import org.app.matriculaservice.infrastructure.adapter.in.web.config.ApiConstants;
import org.springframework.http.ResponseEntity;
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
public class CareerController {

  private final GetAllCareerUseCase getAllCareerUseCase;
  private final CreateCareerUseCase createCareerUseCase;

  public CareerController(GetAllCareerUseCase getAllCareerUseCase, CreateCareerUseCase createCareerUseCase) {
    this.getAllCareerUseCase = getAllCareerUseCase;
    this.createCareerUseCase = createCareerUseCase;
  }

  @GetMapping("/career")
  public ResponseEntity<List<CareerResponse>> findAll() {
    List<CareerResponse> careerList = getAllCareerUseCase.findAll();
    return ResponseEntity.ok(careerList);
  }

  @PostMapping("/career")
  public ResponseEntity<CareerResponse> createCareer(@RequestBody CreateCareerCommand createCareerCommand) {
    CareerResponse careerResponse = createCareerUseCase.createCareer(createCareerCommand);
    return ResponseEntity.ok(careerResponse);
  }

}
