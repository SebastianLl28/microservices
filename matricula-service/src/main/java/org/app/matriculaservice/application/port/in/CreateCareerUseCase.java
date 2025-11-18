package org.app.matriculaservice.application.port.in;

import org.app.matriculaservice.application.dto.command.CreateCareerCommand;
import org.app.matriculaservice.application.dto.response.CareerResponse;

/**
 * @author Alonso
 */
public interface CreateCareerUseCase {

  CareerResponse createCareer(CreateCareerCommand createCareerCommand);

}
