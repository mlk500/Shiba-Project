package sheba.backend.app.BL;

import org.springframework.stereotype.Service;
import sheba.backend.app.entities.QuestionTask;
import sheba.backend.app.entities.Task;
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

    public QuestionTask updateQuestionTask(Long taskId, Long questionTaskId, QuestionTask questionTaskDetails) {
        QuestionTask questionTask = questionTaskRepository.findByQuestionTaskIdAndTaskId(questionTaskId, taskId)
                .orElseThrow(() -> new RuntimeException("No QuestionTask found with id " + questionTaskId + " for Task " + taskId));

        if (questionTaskDetails.getCorrectAnswer() == null) {
            throw new IllegalArgumentException("Correct answer must not be null");
        }

        questionTask.setQuestion(questionTaskDetails.getQuestion());
        questionTask.setAnswers(questionTaskDetails.getAnswers());
        questionTask.setCorrectAnswer(questionTaskDetails.getCorrectAnswer());

        return questionTaskRepository.save(questionTask);
    }
//    public Optional<QuestionTask> getQuestionTask(Long id) {
//        return questionTaskRepository.findById(id);
//    }

//    public List<QuestionTask> getAllQuestionTasks() {
//        return questionTaskRepository.findAll();
//    }


    public void deleteQuestionTask(QuestionTask questionTask) {
        questionTaskRepository.deleteById(questionTask.getQuestionTaskID());
    }
}
