package org.app.matriculaservice.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import org.app.matriculaservice.domain.model.Career;
import org.app.matriculaservice.domain.repository.CareerRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.entity.CareerJpaEntity;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.jpa.repository.CareerJpaRepository;
import org.app.matriculaservice.infrastructure.adapter.out.persistence.mapper.CareerJpaMapper;
import org.springframework.stereotype.Component;

/**
 * @author Alonso
 */
@Component
public class CareerRepositoryAdapter implements CareerRepository {

  private final CareerJpaRepository careerJpaRepository;
  private final CareerJpaMapper careerJpaMapper;

  public CareerRepositoryAdapter(CareerJpaRepository careerJpaRepository, CareerJpaMapper careerJpaMapper) {
    this.careerJpaRepository = careerJpaRepository;
    this.careerJpaMapper = careerJpaMapper;
  }

  @Override
  public Optional<Career> findById(Integer id) {
    return Optional.empty();
  }

  @Override
  public Career save(Career career) {
    CareerJpaEntity careerJpaEntity = careerJpaMapper.toJpaEntity(career);
    careerJpaEntity = careerJpaRepository.save(careerJpaEntity);
    return careerJpaMapper.toDomainCareer(careerJpaEntity);
  }

  @Override
  public List<Career> findAll() {
    return careerJpaRepository.findAll().stream().map(
        careerJpaMapper::toDomainCareer
    ).toList();
  }

  @Override
  public List<Career> findByFacultyId(Integer facultyId) {
    return List.of();
  }

  @Override
  public List<Career> findByActive(boolean active) {
    return List.of();
  }

  @Override
  public long countActiveCourses(Integer careerId) {
    return 0;
  }
}
