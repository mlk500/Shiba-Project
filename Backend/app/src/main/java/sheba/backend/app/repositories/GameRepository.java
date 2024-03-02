package sheba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sheba.backend.app.entities.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {


}
