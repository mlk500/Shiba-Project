package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.Task;
import sheba.backend.app.entities.Unit;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
     List<Unit> findByTask(Task task);
}
