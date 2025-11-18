package org.app.matriculaservice.application.service;

import java.time.Clock;
import java.util.List;
import org.app.matriculaservice.application.dto.command.CreateFacultyCommand;
import org.app.matriculaservice.application.dto.command.UpdateFacultyCommand;
import org.app.matriculaservice.application.dto.response.FacultyResponse;
import org.app.matriculaservice.application.mapper.FacultyMapper;
import org.app.matriculaservice.application.port.in.CreateFacultyUseCase;
import org.app.matriculaservice.application.port.in.GetAllFacultyUseCase;
import org.app.matriculaservice.application.port.in.UpdateFacultyUseCase;
import org.app.matriculaservice.domain.exception.AlreadyExistsException;
import org.app.matriculaservice.domain.exception.NotFoundException;
import org.app.matriculaservice.domain.model.Faculty;
import org.app.matriculaservice.domain.repository.FacultyRepository;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */
@Service
public class FacultyApplicationService implements GetAllFacultyUseCase, CreateFacultyUseCase,
  UpdateFacultyUseCase {

  private final FacultyRepository facultyRepository;
  private final FacultyMapper facultyMapper;
  private final Clock clock;

  public FacultyApplicationService(FacultyRepository facultyRepository, FacultyMapper facultyMapper, Clock clock) {
    this.facultyRepository = facultyRepository;
    this.facultyMapper = facultyMapper;
    this.clock = clock;
  }

  private void enforceUniqueName(String name, Integer excludeId) {
    boolean taken = excludeId == null
        ? facultyRepository.existsByNameIgnoreCase(name)
        : facultyRepository.existsByNameIgnoreCaseAndFacultyIdNot(name, excludeId);
    if (taken) {
      throw new AlreadyExistsException("Faculty name already taken: " + name);
    }
  }

  @Override
  public List<FacultyResponse> findAll() {
    List<Faculty> facultyList = facultyRepository.findAll();
    return facultyList.stream().map(facultyMapper::toResponse).toList();
  }

  @Override
  public FacultyResponse createFaculty(CreateFacultyCommand command) {
    Faculty faculty = Faculty.create(command.getName(), command.getDescription(), command.getLocation(), command.getDean(), clock);
    enforceUniqueName(faculty.getName(), null);
    Faculty savedFaculty = facultyRepository.save(faculty);
    return facultyMapper.toResponse(savedFaculty);
  }

  @Override
  public FacultyResponse updateFaculty(UpdateFacultyCommand updateFacultyCommand, Integer facultyId) {
    Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> NotFoundException.ofFaculty(facultyId));
    enforceUniqueName(updateFacultyCommand.getName(), facultyId);
    faculty = faculty.update(updateFacultyCommand.getName(), updateFacultyCommand.getDescription(), updateFacultyCommand.getLocation(), updateFacultyCommand.getDean(), updateFacultyCommand.getActive());
    Faculty updatedFaculty = facultyRepository.save(faculty);
    return facultyMapper.toResponse(updatedFaculty);
  }
}
