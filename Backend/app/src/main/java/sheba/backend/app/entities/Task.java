package sheba.backend.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Task")
@Data
//@Inheritance(strategy = InheritanceType.JOINED)

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskID;
    private String name;
    private String description;

    @ElementCollection
    @CollectionTable(name = "task_text", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "text")
    private List<String> taskFreeTexts;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private QuestionTask questionTask;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MediaTask> mediaList = new ArrayList<>();
}
