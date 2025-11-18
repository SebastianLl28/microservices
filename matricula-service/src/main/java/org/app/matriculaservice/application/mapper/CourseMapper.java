package org.app.matriculaservice.application.mapper;

import java.util.List;
import org.app.matriculaservice.application.dto.response.CourseResponse;
import org.app.matriculaservice.application.dto.response.CourseSummaryResponse;
import org.app.matriculaservice.application.dto.response.StudentSummaryResponse;
import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.Student;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

  private final StudentMapper studentMapper;

  public CourseMapper(StudentMapper studentMapper) {
    this.studentMapper = studentMapper;
  }

  public CourseResponse toResponse(Course course, List<Student> studentList) {
    CourseResponse response = new CourseResponse();
    response.setId(course.getId().getValue());
    response.setCode(course.getCode().getValue());
    response.setName(course.getName());
    response.setDescription(course.getDescription());
    response.setActive(course.isActive());

    List<StudentSummaryResponse> studentSummaries = studentList.stream()
        .map(studentMapper::toStudentSummaryResponse)
        .toList();

    response.setEnrolledStudentList(studentSummaries);

    return response;
  }

  public CourseSummaryResponse toSummaryResponse(Course course) {
    CourseSummaryResponse response = new CourseSummaryResponse();
    response.setId(course.getId().getValue());
    response.setCode(course.getCode().getValue());
    response.setName(course.getName());
    response.setActive(course.isActive());
    return response;
  }
}
