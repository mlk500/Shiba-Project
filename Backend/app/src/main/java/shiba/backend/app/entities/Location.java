package shiba.backend.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Location")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationID;
    private String name;
    private String description;
    private int floor;
    private String locationImg;
    private String QRCode;


    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    //if a location is deleted the objects of the location are deleted as well
    private List<Object> objectsList;
}
