package org.app.matriculaservice.application.service;

import java.time.Clock;
import java.time.Instant;
import org.app.matriculaservice.application.dto.command.AssignStudentToCourseCommand;
import org.app.matriculaservice.application.dto.response.UnassignStudentToCourseCommand;
import org.app.matriculaservice.application.port.in.AssignStudentToCourseUseCase;
import org.app.matriculaservice.application.port.in.UnassignStudentToCourseUseCase;
import org.app.matriculaservice.application.port.out.MatriculaEventPublisherPort;
import org.app.matriculaservice.domain.exception.NotFoundException;
import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.Student;
import org.app.matriculaservice.domain.model.StudentCourse;
import org.app.matriculaservice.domain.repository.CourseRepository;
import org.app.matriculaservice.domain.repository.StudentCourseRepository;
import org.app.matriculaservice.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

/**
 * @author Alonso
 */
@Service
public class AssignStudentToCourseApplicationService implements
  UnassignStudentToCourseUseCase, AssignStudentToCourseUseCase {
  
  private final Clock clock;
  private final StudentCourseRepository studentCourseRepository;
  private final StudentRepository studentRepository;
  private final CourseRepository courseRepository;
  private final MatriculaEventPublisherPort eventPublisher;
  
  public AssignStudentToCourseApplicationService(
    Clock clock,
    StudentCourseRepository studentCourseRepository,
    StudentRepository studentRepository,
    CourseRepository courseRepository,
    MatriculaEventPublisherPort eventPublisher) {
    this.clock = clock;
    this.studentCourseRepository = studentCourseRepository;
    this.studentRepository = studentRepository;
    this.courseRepository = courseRepository;
    this.eventPublisher = eventPublisher;
  }
  
  @Override
  public void assignStudentToCourse(AssignStudentToCourseCommand cmd) {
    Integer studentId = cmd.getStudentId();
    Integer courseId = cmd.getCourseId();
    
    Student student = studentRepository.findById(studentId)
      .orElseThrow(() -> NotFoundException.ofStudent(studentId));
    
    Course course = courseRepository.findById(courseId)
      .orElseThrow(() -> NotFoundException.ofCourse(courseId));
    
    Instant now = Instant.now(clock);
    
    StudentCourse studentCourse = StudentCourse.assign(
      student.getId(), course.getId(), now);
    
    studentCourseRepository.save(studentCourse);
    
    eventPublisher.publishStudentEnrolled(student, course);
  }
  
  @Override
  public void unassignStudentFromCourse(UnassignStudentToCourseCommand cmd) {
    Integer studentId = cmd.getStudentId();
    Integer courseId = cmd.getCourseId();
    
    Student student = studentRepository.findById(studentId)
      .orElseThrow(() -> NotFoundException.ofStudent(studentId));
    
    Course course = courseRepository.findById(courseId)
      .orElseThrow(() -> NotFoundException.ofCourse(courseId));
    
    Instant now = Instant.now(clock);
    
    StudentCourse studentCourse = studentCourseRepository
      .findByStudentIdAndCourseId(student.getId().getValue(), course.getId().getValue());
    
    studentCourse.unassign(now);
    studentCourseRepository.save(studentCourse);
    
    eventPublisher.publishStudentUnenrolled(student, course);
    
  }
}
