package com.educare.dto.response;

import com.educare.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long studentId;
    private String name;
    private LocalDate dob;
    private Student.Gender gender;
    private String contactInfo;
    private String program;
    private Student.StudentStatus status;

    public static StudentResponse from(Student s) {
        return StudentResponse.builder()
            .studentId(s.getStudentId())
            .name(s.getName())
            .dob(s.getDob())
            .gender(s.getGender())
            .contactInfo(s.getContactInfo())
            .program(s.getProgram())
            .status(s.getStatus())
            .build();
    }
}
