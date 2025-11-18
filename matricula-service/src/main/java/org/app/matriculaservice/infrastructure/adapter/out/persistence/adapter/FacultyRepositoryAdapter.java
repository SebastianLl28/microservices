package org.app.matriculaservice.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.app.matriculaservice.domain.model.Faculty;
import org.app.matriculaservice.domain.repository.FacultyRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.FacultyJpaEntity;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository.FacultyJpaRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper.FacultyJpaMapper;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class FacultyRepositoryAdapter implements FacultyRepository {

  private final FacultyJpaRepository facultyJpaRepository;
  private final FacultyJpaMapper facultyJpaMapper;

  public FacultyRepositoryAdapter(FacultyJpaRepository facultyJpaRepository, FacultyJpaMapper facultyJpaMapper) {
    this.facultyJpaRepository = facultyJpaRepository;
    this.facultyJpaMapper = facultyJpaMapper;
  }

  @Override
  public List<Faculty> findAll() {
    return facultyJpaRepository.findAll().stream().map(facultyJpaMapper::toDomainFaculty).toList();
  }

  @Override
  public List<Faculty> findAllByIdIn(Set<Integer> ids) {
    return facultyJpaRepository.findAllByFacultyIdIn(ids).stream().map(facultyJpaMapper::toDomainFaculty).toList();
  }

  @Override
  public Faculty save(Faculty faculty) {
    FacultyJpaEntity facultyJpaEntity = facultyJpaMapper.toJpaEntity(faculty);
    facultyJpaEntity = facultyJpaRepository.save(facultyJpaEntity);
    return facultyJpaMapper.toDomainFaculty(facultyJpaEntity);
  }

  @Override
  public Optional<Faculty> findById(Integer id) {
    return facultyJpaRepository.findById(id).map(facultyJpaMapper::toDomainFaculty);
  }

  @Override
  public boolean existsByNameIgnoreCase(String name) {
    return facultyJpaRepository.existsByNameIgnoreCase(name);
  }

  @Override
  public boolean existsByNameIgnoreCaseAndFacultyIdNot(String name, Integer facultyId) {
    return facultyJpaRepository.existsByNameIgnoreCaseAndFacultyIdNot(name, facultyId);
  }

}
