package sheba.backend.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sheba.backend.app.enums.UserRole;

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
    private String password;
    private String color;
    private String sector; //might be enum
    @Enumerated(EnumType.STRING)
    private UserRole role;

    
    //if an admin is deleted the games are not deleted
    @JsonIgnore
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = false)

    private List<Game> gamesList;

    @JsonIgnore
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Task> tasksList;


}