package sheba.backend.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationImgID;
    private String name;
    private String type;
    private String imagePath;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_location_id")
    private Location location;

}
