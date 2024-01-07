package shiba.backend.app.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Location Image")
@Data
public class LocationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationImgID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_location_id")
    private Location location;
}
