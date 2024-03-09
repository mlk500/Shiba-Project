package sheba.backend.app.BL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sheba.backend.app.entities.QuestionTask;
import sheba.backend.app.repositories.QuestionTaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionTaskBL {
    private final QuestionTaskRepository questionTaskRepository;

    public QuestionTaskBL(QuestionTaskRepository questionTaskRepository) {
        this.questionTaskRepository = questionTaskRepository;
    }

    public QuestionTask createQuestionTask(QuestionTask questionTask) {
        if (questionTask.getCorrectAnswer() == null) {
            throw new IllegalArgumentException("Correct answer cannot be null");
        }
        return questionTaskRepository.save(questionTask);
    }

    public Optional<QuestionTask> getQuestionTask(Long id) {
        return questionTaskRepository.findById(id);
    }

    public List<QuestionTask> getAllQuestionTasks() {
        return questionTaskRepository.findAll();
    }

    public QuestionTask updateQuestionTask(Long id, QuestionTask questionTaskDetails) {
        if (questionTaskDetails.getCorrectAnswer() == null) {
            throw new IllegalArgumentException("Correct answer must not be null");
        }

        QuestionTask questionTask = questionTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuestionTask not found with id " + id));
        questionTask.setName(questionTaskDetails.getName());
        questionTask.setDescription(questionTaskDetails.getDescription());
        questionTask.setQuestion(questionTaskDetails.getQuestion());
        questionTask.setAnswers(questionTaskDetails.getAnswers());
        questionTask.setCorrectAnswer(questionTaskDetails.getCorrectAnswer());

        return questionTaskRepository.save(questionTask);
    }

    public void deleteQuestionTask(Long id) {
        questionTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuestionTask not found with id " + id));
        questionTaskRepository.deleteById(id);
    }
}
