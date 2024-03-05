package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.LocationImage;

import java.util.Optional;


public interface LocationImageRepository extends JpaRepository<LocationImage, Long> {
    Optional<LocationImage> findByName(String filename);
}
