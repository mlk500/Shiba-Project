package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.ObjectImage;

public interface ObjectImageRepository extends JpaRepository<ObjectImage, Long> {

}
