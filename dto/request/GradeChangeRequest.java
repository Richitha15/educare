package com.educare.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeChangeRequest {
    @NotNull(message = "Grade ID is required")
    private Long gradeId;
    private String reason;
    private String evidenceUri;
}
