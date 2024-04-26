package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sheba.backend.app.entities.MediaTask;

import java.util.List;

public interface MediaTaskRepository extends JpaRepository<MediaTask, Long> {

    @Query("SELECT mt.fileName FROM MediaTask mt WHERE mt.task.taskID = :taskId")
    List<String> findFileNamesByTaskId(Long taskId);

    @Query("SELECT mt FROM MediaTask mt WHERE mt.task.taskID = :taskId")
    List<MediaTask> findAllByTaskId(Long taskId);
}
