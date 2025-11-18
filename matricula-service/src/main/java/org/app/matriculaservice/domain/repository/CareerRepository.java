package org.app.matriculaservice.domain.repository;

import java.util.List;
import java.util.Optional;
import org.app.matriculaservice.domain.model.Career;

public interface CareerRepository {
  Optional<Career> findById(Integer id);
  Career save(Career career);
  List<Career> findAll();
  List<Career> findByFacultyId(Integer facultyId);
  List<Career> findByActive(boolean active);

  long countActiveCourses(Integer careerId);
}
