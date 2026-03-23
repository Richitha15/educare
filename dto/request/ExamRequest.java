package com.educare.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExamRequest {
    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "Scheduled date is required")
    private LocalDateTime scheduledAt;

    @NotBlank(message = "Subject is required")
    private String subject;
}
