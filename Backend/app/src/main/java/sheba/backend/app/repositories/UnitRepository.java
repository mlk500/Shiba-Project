package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {
}
