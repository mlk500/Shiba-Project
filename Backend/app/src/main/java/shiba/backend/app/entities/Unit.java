package shiba.backend.app.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Unit")
@Data
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long unitID;
    private String name;
    private String hint;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private Object object;

}
