package sheba.backend.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "media")
@Data
public class MediaTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mediaTaskID;

    private String fileName;
    private String mediaPath;
    private String mediaType;
//    private long fileSize;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date uploadedDate = new Date();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_task_id")
    private Task task;
}

