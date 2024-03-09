package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.GameImage;

public interface GameImageRepository extends JpaRepository<GameImage, Long> {
}
