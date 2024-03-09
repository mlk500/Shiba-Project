package sheba.backend.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Object")
@Data
public class ObjectLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long objectID;
    private String name;
    private String description;
//    private String objectImg;

    //many to one with location
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_location_id")
    private Location location;

    // one to many with object image
    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL, orphanRemoval = true)
    //if an object is deleted the images are deleted as well
    private List<ObjectImage> objectImages;

//    // many to many with task
//    @ManyToMany
//    @JoinTable(name = "object_task",
//            joinColumns = @JoinColumn(name = "game_id"),
//            inverseJoinColumns = @JoinColumn(name = "task_id"))
//    private List<Task> taskList;


}
