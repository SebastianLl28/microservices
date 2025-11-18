package org.app.matriculaservice.domain.repository;

import java.util.Optional;
import org.app.matriculaservice.domain.model.Teacher;
import org.app.matriculaservice.domain.model.valueobjects.TeacherID;

public interface TeacherRepository {

  Optional<Teacher> findById(String id);

  void mustExist(TeacherID id);

}
