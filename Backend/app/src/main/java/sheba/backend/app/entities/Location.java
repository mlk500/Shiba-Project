package sheba.backend.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Table(name = "Location")
@Data
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationID;
    private String name;
    private String description;
    private int floor;
    private String QRCode;

    @OneToOne(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LocationImage locationImage;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    //if a location is deleted the objects of the location are deleted as well
    private List<LocationObject> objectsList;

}
