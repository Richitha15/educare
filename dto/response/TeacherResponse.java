package com.educare.dto.response;

import com.educare.model.Teacher;
import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class TeacherResponse {
    private Long teacherId;
    private String name;
    private String department;
    private String contactInfo;
    private Teacher.TeacherStatus status;

    public static TeacherResponse from(Teacher t) {
        return TeacherResponse.builder()
            .teacherId(t.getTeacherId()).name(t.getName())
            .department(t.getDepartment()).contactInfo(t.getContactInfo())
            .status(t.getStatus()).build();
    }
}
