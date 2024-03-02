package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.Location;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    public Location findByLocationID(long id);

}
