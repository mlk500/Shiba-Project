package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.QuestionTask;

public interface QuestionTaskRepository extends JpaRepository<QuestionTask, Long> {
}
