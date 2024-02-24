package shiba.backend.app.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "object_image")
@Data
public class ObjectImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long objectImageID;

    //many to one with
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_object_id")
    private Object object;

}
