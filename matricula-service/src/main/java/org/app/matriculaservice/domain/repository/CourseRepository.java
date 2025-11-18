package org.app.matriculaservice.domain.repository;


import java.util.List;
import java.util.Optional;
import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.valueobjects.CourseCode;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;

public interface CourseRepository {
  Optional<Course> findById(Integer id);
  Course save(Course course);

  boolean existsByCodeAndCareerId(CourseCode code, Integer careerId);

  void mustExist(CourseID id);


  List<Course> findByCareerId(Integer careerId);
  List<Course> findByCareerIdAndActive(Integer careerId, boolean active);
  List<Course> findByCareerIdAndSemesterLevel(Integer careerId, int level);

  List<Course> findAll();

//  List<Course> findByCareerIdWithActiveCourses(Integer careerId);


  List<Course> findByCareerIdWithActiveCourses(Integer careerId);
}
