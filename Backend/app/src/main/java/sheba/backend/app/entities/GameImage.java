package sheba.backend.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "game_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameImgID;
    private String name;
    private String type;
    private String imagePath;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_game_id")
    private Game game;
}
