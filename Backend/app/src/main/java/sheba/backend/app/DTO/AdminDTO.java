package sheba.backend.app.DTO;

import org.springframework.beans.factory.annotation.Autowired;
import sheba.backend.app.entities.Task;
import sheba.backend.app.enums.UserRole;
import sheba.backend.app.mappers.AdminMapper;
import sheba.backend.app.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminDTO {
    private long adminID;
    private String username;
    private String sector;
    private UserRole role;

    @Autowired
    private TaskRepository taskRepository;
    public long getAdminID() {
        return adminID;
    }

    public void setAdminID(long adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

//    public Optional<Task> getTask(Long id) {
//        return taskRepository.findById(id).map(task -> {
//            task.setAdminDTO(AdminMapper.INSTANCE.adminToAdminDTO(task.getAdmin()));
//            return task;
//        });
//    }
//
//    public List<Task> getAllTasks() {
//        return taskRepository.findAll().stream().map(task -> {
//            task.setAdminDTO(AdminMapper.INSTANCE.adminToAdminDTO(task.getAdmin()));
//            return task;
//        }).collect(Collectors.toList());
//    }


}
