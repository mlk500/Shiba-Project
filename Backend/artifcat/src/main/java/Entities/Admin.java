package Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name="Admin")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adminID;
    private List<String> sector;
    private String password;

}
