package shiba.backend.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Game")
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameID;
    private String gameName;
    private String description;
    private String gameImg; //tbd

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "game")
    private List<Unit> units;
}
