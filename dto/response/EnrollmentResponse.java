package com.educare.dto.response;

import com.educare.model.EnrollmentRecord;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class EnrollmentResponse {
    private Long enrollId;
    private Long studentId;
    private String studentName;
    private Long classId;
    private String className;
    private LocalDateTime enrolledAt;
    private EnrollmentRecord.EnrollStatus status;

    public static EnrollmentResponse from(EnrollmentRecord e) {
        return EnrollmentResponse.builder()
            .enrollId(e.getEnrollId())
            .studentId(e.getStudent().getStudentId())
            .studentName(e.getStudent().getName())
            .classId(e.getSchoolClass().getClassId())
            .className(e.getSchoolClass().getName())
            .enrolledAt(e.getEnrolledAt())
            .status(e.getStatus()).build();
    }
}
