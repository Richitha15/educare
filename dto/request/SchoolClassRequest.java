package com.educare.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SchoolClassRequest {
    @NotBlank(message = "Class name is required")
    private String name;
    private String gradeLevel;
    private String scheduleJson;
    private Integer capacity;
}
