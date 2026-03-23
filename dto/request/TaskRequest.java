package com.educare.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskRequest {
    @NotNull(message = "Assigned user ID is required")
    private Long assignedToUserId;

    @NotBlank(message = "Description is required")
    private String description;

    private LocalDate dueDate;
    private Long relatedEntityId;
}
