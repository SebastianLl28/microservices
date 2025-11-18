package org.app.matriculaservice.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.app.matriculaservice.domain.model.Faculty;

/**
 * @author Alonso
 */
public interface FacultyRepository {

  List<Faculty> findAll();

  List<Faculty> findAllByIdIn(Set<Integer> ids);

  Faculty save(Faculty faculty);

  Optional<Faculty> findById(Integer id);

  boolean existsByNameIgnoreCase(String name);

  boolean existsByNameIgnoreCaseAndFacultyIdNot(String name, Integer facultyId);
}
