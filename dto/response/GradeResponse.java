package com.educare.dto.response;

import com.educare.model.Grade;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class GradeResponse {
    private Long gradeId;
    private Long studentId;
    private String studentName;
    private Long examId;
    private String examSubject;
    private BigDecimal gradeValue;
    private String submittedByName;
    private LocalDateTime submittedAt;
    private Grade.GradeStatus status;

    public static GradeResponse from(Grade g) {
        return GradeResponse.builder()
            .gradeId(g.getGradeId())
            .studentId(g.getStudent().getStudentId())
            .studentName(g.getStudent().getName())
            .examId(g.getExam().getExamId())
            .examSubject(g.getExam().getSubject())
            .gradeValue(g.getGradeValue())
            .submittedByName(g.getSubmittedBy() != null ? g.getSubmittedBy().getName() : null)
            .submittedAt(g.getSubmittedAt())
            .status(g.getStatus()).build();
    }
}
