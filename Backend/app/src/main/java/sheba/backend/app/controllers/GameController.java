package sheba.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheba.backend.app.BL.GameBL;
import sheba.backend.app.entities.Game;
import sheba.backend.app.util.Endpoints;

import java.util.Optional;

@RestController
@RequestMapping(Endpoints.GAME_ENDPOINT)
public class GameController {
    @Autowired
    private GameBL gameBL;

    @PostMapping("create")
    public ResponseEntity<?> createGame(@RequestBody Game game) {

        try{
            Game createdGame  = gameBL.createGame(game);
            System.out.println("Game is " + createdGame);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("getAll")
    public ResponseEntity<?>  getAllGames() {
        return ResponseEntity.ok(gameBL.getAllGames());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        Optional<Game> game = gameBL.getGameById(id);
        return game.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        try {
            Game updatedGame = gameBL.updateGame(id, gameDetails);
            return ResponseEntity.ok(updatedGame);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating the game: " + e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id) {
        try {
            gameBL.deleteGame(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting the game: " + e.getMessage());
        }
    }
}
