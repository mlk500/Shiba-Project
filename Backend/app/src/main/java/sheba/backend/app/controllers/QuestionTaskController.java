package sheba.backend.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheba.backend.app.entities.QuestionTask;
import sheba.backend.app.BL.QuestionTaskBL;
import sheba.backend.app.util.Endpoints;

@RestController
@RequestMapping(Endpoints.QUESTION_TASK_ENDPOINT)
public class QuestionTaskController {

    private final QuestionTaskBL questionTaskBL;

    public QuestionTaskController(QuestionTaskBL questionTaskBL) {
        this.questionTaskBL = questionTaskBL;
    }

//    @PostMapping("create")
//    public ResponseEntity<?> createQuestionTask(@RequestBody QuestionTask questionTask) {
//        try {
//            QuestionTask createdTask = questionTaskBL.createQuestionTask(questionTask);
//            return ResponseEntity.ok(createdTask);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

//    @GetMapping("get/{id}")
//    public ResponseEntity<QuestionTask> getQuestionTask(@PathVariable Long id) {
//        return questionTaskBL.getQuestionTask(id)
//                .map(task -> ResponseEntity.ok().body(task))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("getAll")
//    public ResponseEntity<?> getAllQuestionTasks() {
//        return ResponseEntity.ok(questionTaskBL.getAllQuestionTasks());
//    }

//    @PutMapping("update/{id}")
//    public ResponseEntity<?> updateQuestionTask(@PathVariable Long id, @RequestBody QuestionTask questionTask) {
//        try {
//            QuestionTask updatedTask = questionTaskBL.updateQuestionTask(id, questionTask);
//            return ResponseEntity.ok(updatedTask);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<?> deleteQuestionTask(@PathVariable Long id) {
//        try {
//            questionTaskBL.deleteQuestionTask(id);
//            return ResponseEntity.ok().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}

