package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sheba.backend.app.entities.QuestionTask;

import java.util.Optional;

public interface QuestionTaskRepository extends JpaRepository<QuestionTask, Long> {

    @Query("SELECT qt FROM QuestionTask qt WHERE qt.questionTaskID = :questionTaskId AND qt.task.taskID = :taskId")
    Optional<QuestionTask> findByQuestionTaskIdAndTaskId(Long questionTaskId, Long taskId);
}
