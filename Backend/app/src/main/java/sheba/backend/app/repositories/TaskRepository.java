package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public Task findByTaskID(long id);
}
