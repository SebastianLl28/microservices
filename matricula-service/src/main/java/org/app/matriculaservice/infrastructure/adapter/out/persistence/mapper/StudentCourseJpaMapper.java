package org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper;

import java.time.Clock;
import org.app.matriculaservice.domain.model.StudentCourse;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.model.valueobjects.StudentCourseID;
import org.app.matriculaservice.domain.model.valueobjects.StudentID;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.StudentCourseJpaEntity;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class StudentCourseJpaMapper {

  private final Clock clock;

  public StudentCourseJpaMapper(Clock clock) {
    this.clock = clock;
  }

  public StudentCourse toDomain(StudentCourseJpaEntity studentCourseJpaEntity) {

    StudentCourseID studentCourseID = new StudentCourseID(studentCourseJpaEntity.getStudentCourseId());
    StudentID studentID = new StudentID(studentCourseJpaEntity.getStudentId());
    CourseID courseID = new CourseID(studentCourseJpaEntity.getCourseId());

    return StudentCourse.rehydrate(
        studentCourseID,
        studentID,
        courseID,
        clock.instant(),
        null,
        studentCourseJpaEntity.getActive()
    );
  };

  public StudentCourseJpaEntity toJpaEntity(StudentCourse studentCourse) {
    StudentCourseJpaEntity studentCourseJpaEntity = new StudentCourseJpaEntity();

    if (studentCourse.getStudentCourseId() != null) {
      studentCourseJpaEntity.setStudentCourseId(studentCourse.getStudentCourseId().getValue());
    }

    studentCourseJpaEntity.setStudentId(studentCourse.getStudentId().getValue());
    studentCourseJpaEntity.setCourseId(studentCourse.getCourseId().getValue());
    studentCourseJpaEntity.setActive(studentCourse.isActive());
    return studentCourseJpaEntity;
  }
}
