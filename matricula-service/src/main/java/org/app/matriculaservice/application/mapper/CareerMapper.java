package org.app.matriculaservice.application.mapper;

import java.util.List;
import org.app.matriculaservice.application.dto.response.CareerResponse;
import org.app.matriculaservice.application.dto.response.CourseSummaryResponse;
import org.app.matriculaservice.application.dto.response.FacultySummaryResponse;
import org.app.matriculaservice.domain.model.Career;
import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.Faculty;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class CareerMapper {

  private final CourseMapper courseMapper;

  public CareerMapper(CourseMapper courseMapper) {
    this.courseMapper = courseMapper;
  }

  public CareerResponse toResponse(Career career, Faculty faculty, List<Course> courseList) {
    CareerResponse response = new CareerResponse();
    response.setId(career.getId().getValue());
    response.setName(career.getName());
    response.setDescription(career.getDescription());
    response.setSemesterLength(career.getSemesterLength());
    response.setDegreeAwarded(career.getDegreeAwarded().getValue());
    response.setRegistrationDate(career.getRegistrationDate());
    response.setActive(career.isActive());

    FacultySummaryResponse facultySummaryResponse = new FacultySummaryResponse();
    facultySummaryResponse.setId(faculty.getId().getValue());
    facultySummaryResponse.setName(faculty.getName());

    response.setFaculty(facultySummaryResponse);

    List<CourseSummaryResponse> courseSummaryResponseList = courseList.stream().map(courseMapper::toSummaryResponse).toList();

    response.setCourseList(courseSummaryResponseList);

    return response;
  }

}
