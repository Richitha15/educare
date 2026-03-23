package com.educare.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeacherAssignmentRequest {
    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Class ID is required")
    private Long classId;
}
