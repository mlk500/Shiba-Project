package shiba.backend.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shiba.backend.app.enums.UserRole;

import java.util.List;

@Entity
@Table(name = "Admin")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adminID;
    @Column(unique = true)
    private String username;
    private String sector; //might be enum
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    //if an admin is deleted the games are not deleted
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = false)

    private List<Game> gamesList;


}