package sheba.backend.app.BL;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sheba.backend.app.entities.MediaTask;
import sheba.backend.app.entities.QuestionTask;
import sheba.backend.app.entities.Task;
import sheba.backend.app.exceptions.TaskCannotBeEmpty;
import sheba.backend.app.repositories.TaskRepository;
import sheba.backend.app.util.StoragePath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TaskBL {

    private final TaskRepository taskRepository;
    private final QuestionTaskBL questionTaskBL;
    private final MediaTaskBL mediaTaskBL;

    public TaskBL(TaskRepository taskRepository, QuestionTaskBL questionTaskBL, MediaTaskBL mediaTaskBL) {
        this.taskRepository = taskRepository;
        this.questionTaskBL = questionTaskBL;
        this.mediaTaskBL = mediaTaskBL;
    }

    // use when adding task items
    public Task createTask(Task task, QuestionTask questionTask, List<MultipartFile> media) throws TaskCannotBeEmpty, IOException {

        if (questionTask == null && media == null && task.getTaskFreeTexts().isEmpty()) {
            throw new TaskCannotBeEmpty("Task must contain at least one item.");
        }

        task = taskRepository.save(task);

        createTaskItems(task, questionTask, media);

        return taskRepository.save(task);
    }

    public Task updateTask(Long taskID, Task newTask, QuestionTask questionTask, List<MultipartFile> media) throws TaskCannotBeEmpty, IOException, IllegalArgumentException {
        Task task = taskRepository.findById(taskID)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskID));

        if (questionTask == null && media == null && task.getTaskFreeTexts().isEmpty() && newTask.getTaskFreeTexts().isEmpty()) {
            throw new TaskCannotBeEmpty("Task must contain at least one item.");
        }

        if (newTask != null) {
            updateTaskFields(task, newTask);
        }

        createTask(task, questionTask, media);

        return taskRepository.save(task);
    }


    private void updateTaskFields(Task task, Task newTask) {
        task.setName(newTask.getName());
        task.setTaskFreeTexts(newTask.getTaskFreeTexts());
        task.setDescription(newTask.getDescription());
    }

    public QuestionTask updateTaskQuestion(Long taskId, Long questionTaskId, QuestionTask questionTask) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        return questionTaskBL.updateQuestionTask(taskId, questionTaskId, questionTask);
    }


    public Task removeMediaFromTask(Long taskId, Long mediaId) throws TaskCannotBeEmpty, IOException, IllegalArgumentException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        MediaTask mediaToRemove = task.getMediaList().stream()
                .filter(media -> media.getMediaTaskID() == mediaId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Media not found with id: " + mediaId));

        if (task.getMediaList().size() < 2 && task.getTaskFreeTexts().isEmpty() && task.getQuestionTask() == null) {
            throw new TaskCannotBeEmpty("Removing this item leads to an empty task, delete task instead or add at least one item");
        }

        task.getMediaList().remove(mediaToRemove);
        mediaTaskBL.deleteMedia(mediaToRemove);

        if (task.getQuestionTask() == null && task.getMediaList().isEmpty()) {
            throw new TaskCannotBeEmpty("Task must contain at least one item after media removal.");
        }
        return taskRepository.save(task);
    }


    public Task removeQuestionFromTask(Long taskId) throws TaskCannotBeEmpty, IllegalArgumentException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        if (task.getMediaList().isEmpty() || task.getTaskFreeTexts().isEmpty()) {
            throw new TaskCannotBeEmpty("Removing this item leads to an empty task, delete task instead or add at least one item");
        }
        if (task.getQuestionTask() != null) {
            questionTaskBL.deleteQuestionTask(task.getQuestionTask());
            task.setQuestionTask(null);
        }
        return taskRepository.save(task);
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTask(Long taskId) throws IOException, RuntimeException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        mediaTaskBL.deleteAllMediaForTask(taskId);
        if (task.getQuestionTask() != null) {
            questionTaskBL.deleteQuestionTask(task.getQuestionTask());
        }

        String baseDirectory = StoragePath.MEDIA_TASK_PATH;
        Path taskDirectory = Paths.get(baseDirectory + File.separator + "task" + taskId);
        if (Files.exists(taskDirectory)) {
            try (Stream<Path> paths = Files.walk(taskDirectory)) {
                paths.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(file -> {
                            if (!file.delete()) {
                                throw new RuntimeException("Failed to delete file " + file);
                            }
                        });
            }
        }

        taskRepository.delete(task);
    }


    private void createTaskItems(Task task, QuestionTask questionTask, List<MultipartFile> media) throws IOException {
        if (questionTask != null) {
            if (task.getQuestionTask() != null) {
                updateTaskQuestion(task.getTaskID(), task.getQuestionTask().getQuestionTaskID(),
                        questionTask);
            } else {
                questionTask.setTask(task);
                task.setQuestionTask(questionTaskBL.createQuestionTask(questionTask));
            }
        }

        if (media != null && !media.isEmpty()) {
            for (MultipartFile file : media) {
                MediaTask savedMedia = mediaTaskBL.createMedia(task, file);
                task.getMediaList().add(savedMedia);
                savedMedia.setTask(task);
            }
        }
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}


