package org.app.matriculaservice.application.service;

import java.time.Clock;
import java.util.List;
import org.app.matriculaservice.application.dto.command.CreateCourseCommand;
import org.app.matriculaservice.application.dto.response.CourseResponse;
import org.app.matriculaservice.application.mapper.CourseMapper;
import org.app.matriculaservice.application.port.in.CreateCourseUseCase;
import org.app.matriculaservice.application.port.in.GetAllCourseUseCase;
import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.Student;
import org.app.matriculaservice.domain.model.valueobjects.CareerID;
import org.app.matriculaservice.domain.model.valueobjects.CourseCode;
import org.app.matriculaservice.domain.model.valueobjects.Credits;
import org.app.matriculaservice.domain.model.valueobjects.SemesterLevel;
import org.app.matriculaservice.domain.repository.CourseRepository;
import org.app.matriculaservice.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */
@Service
public class CourseApplicationService implements GetAllCourseUseCase, CreateCourseUseCase {

  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;
  private final Clock clock;
  private final StudentRepository studentRepository;

  public CourseApplicationService(CourseRepository courseRepository, CourseMapper courseMapper, Clock clock, StudentRepository studentRepository) {
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
    this.clock = clock;
    this.studentRepository = studentRepository;
  }

  @Override
  public List<CourseResponse> findAll() {
    List<Course> courseList = courseRepository.findAll();

    return courseList.stream()
        .map(course -> {
          List<Student> enrolledStudents = studentRepository.findAllByCourseId
          (course.getId().getValue());
          return courseMapper.toResponse(course, enrolledStudents);
        })
        .toList();
  }

  @Override
  public CourseResponse createCourse(CreateCourseCommand createCourseCommand) {
    CareerID careerID = new CareerID(createCourseCommand.getCareerId());
    CourseCode courseCode = new CourseCode(createCourseCommand.getCode());
    Credits credits = new Credits(createCourseCommand.getCredits());
    SemesterLevel semesterLevel = new SemesterLevel(createCourseCommand.getSemesterLevel());

    Course course = Course.create(careerID, courseCode, createCourseCommand.getName(), createCourseCommand.getDescription(), credits, semesterLevel, clock);

    Course savedCourse = courseRepository.save(course);
    return courseMapper.toResponse(savedCourse, List.of());
  }
}
