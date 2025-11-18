package org.app.matriculaservice.application.service;

import java.time.Clock;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.app.matriculaservice.application.dto.command.CreateCareerCommand;
import org.app.matriculaservice.application.dto.response.CareerResponse;
import org.app.matriculaservice.application.mapper.CareerMapper;
import org.app.matriculaservice.application.port.in.CreateCareerUseCase;
import org.app.matriculaservice.application.port.in.GetAllCareerUseCase;
import org.app.matriculaservice.domain.exception.NotFoundException;
import org.app.matriculaservice.domain.model.Career;
import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.Faculty;
import org.app.matriculaservice.domain.model.valueobjects.DegreeTitle;
import org.app.matriculaservice.domain.model.valueobjects.FacultyID;
import org.app.matriculaservice.domain.repository.CareerRepository;
import org.app.matriculaservice.domain.repository.CourseRepository;
import org.app.matriculaservice.domain.repository.FacultyRepository;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */
@Service
public class CareerApplicationService implements GetAllCareerUseCase, CreateCareerUseCase {

  private final CareerRepository careerRepository;
  private final FacultyRepository facultyRepository;
  private final CareerMapper careerMapper;
  private final Clock clock;
  private final CourseRepository courseRepository;

  public CareerApplicationService(CareerRepository careerRepository, CareerMapper careerMapper, FacultyRepository facultyRepository, Clock clock, CourseRepository courseRepository) {
    this.careerRepository = careerRepository;
    this.careerMapper = careerMapper;
    this.facultyRepository = facultyRepository;
    this.clock = clock;
    this.courseRepository = courseRepository;
  }

  @Override
  public List<CareerResponse> findAll() {

    List<Career> careerList = careerRepository.findAll();

    Set<Integer> facultyIds = careerList.stream().map(c -> c.getFacultyId().getValue()).collect(
      Collectors.toSet());

    List<Faculty> facultyMap = facultyRepository.findAllByIdIn(facultyIds);

    return careerList.stream().map(career -> {
      Faculty faculty = facultyMap.stream().filter(f -> f.getId().getValue().equals(career.getFacultyId().getValue())).findFirst().orElseThrow();
      List<Course> courseList = courseRepository.findByCareerIdWithActiveCourses(career.getId().getValue());
      return careerMapper.toResponse(career, faculty, courseList);
    }).toList();
  }

  @Override
  public CareerResponse createCareer(CreateCareerCommand command) {
    FacultyID facultyID = new FacultyID(command.getFacultyId());
    DegreeTitle degreeTitle = new DegreeTitle(command.getDegreeAwarded());

    Career career = Career.create(facultyID, command.getName(), command.getDescription(), command.getSemesterLength(), degreeTitle, clock);

    Faculty faculty = facultyRepository.findById(command.getFacultyId()).orElseThrow(
        () -> NotFoundException.ofCareer(facultyID.getValue())
    );

    Career savedCareer = careerRepository.save(career);

    List<Course> courseList = courseRepository.findByCareerIdWithActiveCourses(savedCareer.getId().getValue());

    return careerMapper.toResponse(savedCareer, faculty, courseList);
  }
}
