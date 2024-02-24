package shiba.backend.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shiba.backend.app.entities.Game;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {


}
