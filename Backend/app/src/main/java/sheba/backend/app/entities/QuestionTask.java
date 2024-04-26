package sheba.backend.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "question_task")
@Data
public class QuestionTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionTaskID;
    private String question;

    @ElementCollection
    @CollectionTable(name = "question_answers", joinColumns = @JoinColumn(name = "question_task_id"))
    @Column(name = "answer")
    private List<String> answers;
    private Integer correctAnswer;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_task_id")
    private Task task;

}
