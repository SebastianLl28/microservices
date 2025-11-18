package org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.valueobjects.CourseCode;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.model.valueobjects.Credits;
import org.app.matriculaservice.domain.model.valueobjects.SemesterLevel;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.CourseJpaEntity;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class CourseJpaMapper {

  public Course toDomainCourse(CourseJpaEntity courseJpaEntity) {
    CourseID courseID =  new CourseID(courseJpaEntity.getCourseId());
    CourseCode courseCode = new CourseCode(courseJpaEntity.getCode());
    SemesterLevel semesterLevel = new SemesterLevel(courseJpaEntity.getSemesterLevel());
    Credits credits = new Credits(courseJpaEntity.getCredits());
    Instant registrationDate = courseJpaEntity.getRegistrationDate().toInstant(ZoneOffset.UTC);
    return Course.rehydrate(courseID, null, courseCode, courseJpaEntity.getName(),
        courseJpaEntity.getDescription(), credits, semesterLevel, registrationDate, courseJpaEntity.getActive());
  }

  public CourseJpaEntity toJpaEntity(Course course) {
    CourseJpaEntity courseJpaEntity = new CourseJpaEntity();

    if (course.getId() != null) {
      courseJpaEntity.setCourseId(course.getId().getValue());
    }

    courseJpaEntity.setCareerId(course.getCareerId().getValue());
    courseJpaEntity.setCode(course.getCode().getValue());
    courseJpaEntity.setName(course.getName());
    courseJpaEntity.setDescription(course.getDescription());
    courseJpaEntity.setCredits(course.getCredits().getValue());
    courseJpaEntity.setSemesterLevel(course.getSemesterLevel().getValue());
    courseJpaEntity.setRegistrationDate(Timestamp.from(course.getRegistrationDate()).toLocalDateTime());
    courseJpaEntity.setActive(course.isActive());
    return courseJpaEntity;
  }

}
