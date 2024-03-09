package sheba.backend.app.BL;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sheba.backend.app.entities.Admin;
import sheba.backend.app.entities.Game;
import sheba.backend.app.repositories.AdminRepository;
import sheba.backend.app.repositories.GameRepository;
import sheba.backend.app.security.CustomAdminDetails;

import java.util.List;
import java.util.Optional;

@Service
public class GameBL {
    private final GameRepository gameRepository;
    private final AdminRepository adminRepository;
    public GameBL(GameRepository gameRepository, AdminRepository adminRepository) {
        this.gameRepository = gameRepository;
        this.adminRepository = adminRepository;
    }

    public Game createGame(Game game) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomAdminDetails adminDetails = (CustomAdminDetails) authentication.getPrincipal();
        System.out.println("admin in BL  " + adminDetails);
        Admin admin = adminRepository.findAdminByUsername(adminDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        System.out.println("found him + " + admin);
        game.setAdmin(admin);
        game.setAdminID(admin.getAdminID());
        return gameRepository.save(game);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    public Game updateGame(Long id, Game gameDetails) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomAdminDetails adminDetails = (CustomAdminDetails) authentication.getPrincipal();

        if (!adminDetails.getUsername().equals(existingGame.getAdmin().getUsername()) &&
                !adminDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MAIN_ADMIN"))) {
            throw new RuntimeException("You do not have permission to update this game");
        }

        existingGame.setGameName(gameDetails.getGameName());
        existingGame.setDescription(gameDetails.getDescription());
        // other fields to update
        return gameRepository.save(existingGame);
    }

    public void deleteGame(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomAdminDetails adminDetails = (CustomAdminDetails) authentication.getPrincipal();

        if (!adminDetails.getUsername().equals(game.getAdmin().getUsername()) &&
                !adminDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MAIN_ADMIN"))) {
            throw new RuntimeException("You do not have permission to delete this game");
        }

        gameRepository.deleteById(id);
    }

}
