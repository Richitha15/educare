package com.educare.dto.response;

import com.educare.model.TeacherAssignment;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherAssignmentResponse {
    private Long assignId;
    private Long teacherId;
    private String teacherName;
    private String teacherDepartment;
    private Long classId;
    private String className;
    private String assignedByName;
    private LocalDateTime assignedAt;
    private TeacherAssignment.AssignStatus status;

    public static TeacherAssignmentResponse from(TeacherAssignment ta) {
        return TeacherAssignmentResponse.builder()
            .assignId(ta.getAssignId())
            .teacherId(ta.getTeacher().getTeacherId())
            .teacherName(ta.getTeacher().getName())
            .teacherDepartment(ta.getTeacher().getDepartment())
            .classId(ta.getSchoolClass().getClassId())
            .className(ta.getSchoolClass().getName())
            .assignedByName(ta.getAssignedBy() != null ? ta.getAssignedBy().getName() : null)
            .assignedAt(ta.getAssignedAt())
            .status(ta.getStatus())
            .build();
    }
}
