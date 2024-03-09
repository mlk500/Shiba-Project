package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.ObjectLocation;

public interface ObjectLocationRepository extends JpaRepository<ObjectLocation, Long> {

}
