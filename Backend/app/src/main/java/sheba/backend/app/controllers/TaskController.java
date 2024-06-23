package sheba.backend.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.BL.TaskBL;
import sheba.backend.app.entities.QuestionTask;
import sheba.backend.app.entities.Task;
import sheba.backend.app.exceptions.AdminNotFound;
import sheba.backend.app.exceptions.TaskCannotBeEmpty;
import sheba.backend.app.exceptions.TaskIsPartOfUnit;
import sheba.backend.app.util.Endpoints;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Endpoints.TASK_ENDPOINT)
public class TaskController {
    private final TaskBL taskBL;

    public TaskController(TaskBL taskBL) {
        this.taskBL = taskBL;
    }

    @PostMapping(value = "create", consumes = {"multipart/form-data"})

    public ResponseEntity<?> createTask(@RequestPart("task") Task task,
                                        @RequestPart(value = "question", required = false) QuestionTask questionTask,
                                        @RequestPart(value = "media", required = false) List<MultipartFile> media,
                                        @RequestPart(value = "admin", required = false) String sectorAdmin) {

        try {
            taskBL.createTask(task, questionTask, media, sectorAdmin);
            return ResponseEntity.ok((HttpStatus.CREATED));
        } catch (TaskCannotBeEmpty | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving task: " + e.getMessage());
        } catch (AdminNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating task " + e.getMessage());
        }

    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId,
                                        @RequestPart(value = "task", required = false) Task task,
                                        @RequestPart(value = "question", required = false) QuestionTask questionTask,
                                        @RequestPart(value = "media", required = false) List<MultipartFile> media,
                                        @RequestPart(value = "admin", required = false) String sectorAdmin,
                                        @RequestPart(value = "toBeDeleted", required = false) List<Long> toBeDeletedMediaIds,
                                        @RequestPart(value = "tbdQuestion", required = false) Long tbdQuestion) {
        try {
            Task updatedTask = taskBL.updateTask(taskId, task, questionTask, media, sectorAdmin, toBeDeletedMediaIds, tbdQuestion);
            return ResponseEntity.ok(updatedTask);
        } catch (TaskCannotBeEmpty | IOException | IllegalArgumentException | AdminNotFound e) {
            System.out.println("the error is " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task: " + e.getMessage());
        }
    }


//    @PutMapping("/update-with-existing-media/{taskId}")
//    public ResponseEntity<?> updateTaskWithExistingMedia(@PathVariable Long taskId,
//                                        @RequestPart(value = "task", required = false) Task task,
//                                        @RequestPart(value = "question", required = false) QuestionTask questionTask,
//                                        @RequestPart(value = "media", required = false) List<MultipartFile> media, @RequestPart(value = "admin", required = false) String sectorAdmin) {
//        try {
//            Task updatedTask = taskBL.updateTask(taskId, task, questionTask, media, sectorAdmin);
//            return ResponseEntity.ok(updatedTask);
//        } catch (TaskCannotBeEmpty | IOException | IllegalArgumentException | AdminNotFound e) {
//            System.out.println("the error is " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving task: " + e.getMessage());
//        }
//    }

    @PutMapping("/updateQuestion/{taskId}/{questionTaskId}")
    public ResponseEntity<?> updateTaskQuestion(@PathVariable Long taskId, @PathVariable Long questionTaskId, @RequestBody QuestionTask questionTask) {
        try {
            QuestionTask updatedQuestionTask = taskBL.updateTaskQuestion(taskId, questionTaskId, questionTask);
            return ResponseEntity.ok(updatedQuestionTask);
        } catch (Exception e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving task: " + e.getMessage());
        }
    }

    @DeleteMapping("/removeMedia/{taskId}/{mediaId}")
    public ResponseEntity<?> removeMedia(@PathVariable Long taskId, @PathVariable Long mediaId) {
        try {
            Task task = taskBL.removeMediaFromTask(taskId, mediaId);
            return ResponseEntity.ok(task);
        } catch (TaskCannotBeEmpty | IOException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving task: " + e.getMessage());
        }
    }

    @DeleteMapping("/removeQuestion/{taskId}")
    public ResponseEntity<?> removeQuestion(@PathVariable Long taskId) {
        try {
            Task task = taskBL.removeQuestionFromTask(taskId);
            return ResponseEntity.ok(task);
        } catch (TaskCannotBeEmpty e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving task: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            taskBL.deleteTask(taskId);
            return ResponseEntity.ok().build();
        } catch (TaskIsPartOfUnit e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting task: " + e.getMessage());
        }
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getTaskByID(@PathVariable Long id) {
        Optional<Task> task = taskBL.getTask(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok(taskBL.getAllTasks());
    }
}

