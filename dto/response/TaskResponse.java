package com.educare.dto.response;

import com.educare.model.Task;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long taskId;
    private Long assignedToUserId;
    private String assignedToUserName;
    private Long relatedEntityId;
    private String description;
    private LocalDate dueDate;
    private Task.TaskStatus status;

    public static TaskResponse from(Task t) {
        return TaskResponse.builder()
            .taskId(t.getTaskId())
            .assignedToUserId(t.getAssignedTo() != null ? t.getAssignedTo().getUserId() : null)
            .assignedToUserName(t.getAssignedTo() != null ? t.getAssignedTo().getName() : null)
            .relatedEntityId(t.getRelatedEntityId())
            .description(t.getDescription())
            .dueDate(t.getDueDate())
            .status(t.getStatus())
            .build();
    }
}
