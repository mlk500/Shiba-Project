package sheba.backend.app.controllers;

import com.google.zxing.WriterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.BL.GameBL;
import sheba.backend.app.entities.Game;
import sheba.backend.app.util.Endpoints;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(Endpoints.GAME_ENDPOINT)
public class GameController {
    private final GameBL gameBL;

    public GameController(GameBL gameBL) {
        this.gameBL = gameBL;
    }

    @PostMapping(value = "create", consumes = {"multipart/form-data" })
    public ResponseEntity<?> createGame(@RequestPart("game") Game game,
                                        @RequestPart(value = "image", required = false) MultipartFile image) {
        try{
            Game createdGame  = gameBL.createGame(game, image);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving object images: " + e.getMessage());
        } catch (WriterException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating QR for game: " + e.getMessage());
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
