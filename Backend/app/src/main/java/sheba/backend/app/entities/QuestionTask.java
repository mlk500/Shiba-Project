package sheba.backend.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "question_task")
@Data
public class QuestionTask extends Task {

    private String question;
    private List<String> answers;
    private int correctAnswer;
}
