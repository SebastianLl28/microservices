package org.app.matriculaservice.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import org.app.matriculaservice.domain.model.Course;
import org.app.matriculaservice.domain.model.valueobjects.CourseCode;
import org.app.matriculaservice.domain.model.valueobjects.CourseID;
import org.app.matriculaservice.domain.repository.CourseRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.CourseJpaEntity;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository.CourseJpaRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper.CourseJpaMapper;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class CourseRepositoryAdapter implements CourseRepository {

  private final CourseJpaMapper courseJpaMapper;
  private final CourseJpaRepository courseJpaRepository;

  public CourseRepositoryAdapter(CourseJpaMapper courseJpaMapper, CourseJpaRepository courseJpaRepository) {
    this.courseJpaMapper = courseJpaMapper;
    this.courseJpaRepository = courseJpaRepository;
  }


  @Override
  public Optional<Course> findById(Integer id) {
    return courseJpaRepository.findById(id).map(courseJpaMapper::toDomainCourse);
  }

  @Override
  public Course save(Course course) {
    CourseJpaEntity courseJpaEntity = courseJpaMapper.toJpaEntity(course);
    courseJpaEntity = courseJpaRepository.save(courseJpaEntity);
    return courseJpaMapper.toDomainCourse(courseJpaEntity);
  }

  @Override
  public boolean existsByCodeAndCareerId(CourseCode code, Integer careerId) {
    return false;
  }

  @Override
  public void mustExist(CourseID id) {

  }

  @Override
  public List<Course> findByCareerId(Integer careerId) {
    return List.of();
  }

  @Override
  public List<Course> findByCareerIdAndActive(Integer careerId, boolean active) {
    return List.of();
  }

  @Override
  public List<Course> findByCareerIdAndSemesterLevel(Integer careerId, int level) {
    return List.of();
  }

  @Override
  public List<Course> findAll() {
    return courseJpaRepository.findAll().stream().map(courseJpaMapper::toDomainCourse).toList();
  }

  @Override
  public List<Course> findByCareerIdWithActiveCourses(Integer careerId) {
    return courseJpaRepository.findAllByCareerIdAndActiveIsFalse(careerId).stream().map(courseJpaMapper::toDomainCourse).toList();
  }
}
