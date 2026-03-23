package com.educare.dto.request;

import com.educare.model.Student;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private LocalDate dob;
    private Student.Gender gender;
    private String contactInfo;
    private String program;
    private Student.StudentStatus status;
}
