package shiba.backend.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Admin")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adminID;
    private String sector;
    private String password;
    
    //if an admin is deleted the games are not deleted
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = false)

    private List<Game> gamesList;


}