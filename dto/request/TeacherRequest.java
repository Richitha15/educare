package com.educare.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeacherRequest {
    @NotBlank(message = "Teacher name is required")
    private String name;
    private String department;
    private String contactInfo;
}
