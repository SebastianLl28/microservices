package org.app.matriculaservice.application.port.in;

import java.util.List;
import org.app.matriculaservice.application.dto.response.CareerResponse;

/**
 * @author Alonso
 */
public interface GetAllCareerUseCase {
  List<CareerResponse> findAll();
}
