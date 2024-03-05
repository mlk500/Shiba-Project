package sheba.backend.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Task")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskID;
    private String name;
    private String description;

    //many to many with object
//    @ManyToMany(mappedBy = "taskList")
//    private List<LocationObject> objectList;


}
