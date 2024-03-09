package sheba.backend.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private long adminID;
    private String gameName;
    private String description;



    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GameImage gameImage;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "game")
    private List<Unit> units;

    @Override
    public String toString() {
        return "Game{" +
                "gameID=" + gameID +
                ", gameName='" + gameName + '\'' +
                ", description='" + description + '\'' +
                ", gameImage=" + gameImage +
                ", admin=" + admin.getAdminID() + " "+ admin.getUsername()+
                ", units=" + units+
                '}';
    }
}
