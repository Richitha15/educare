package com.educare.dto.response;

import com.educare.model.Exam;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ExamResponse {
    private Long examId;
    private Long classId;
    private String className;
    private LocalDateTime scheduledAt;
    private String subject;
    private Exam.ExamStatus status;

    public static ExamResponse from(Exam e) {
        return ExamResponse.builder()
            .examId(e.getExamId())
            .classId(e.getSchoolClass().getClassId())
            .className(e.getSchoolClass().getName())
            .scheduledAt(e.getScheduledAt())
            .subject(e.getSubject())
            .status(e.getStatus()).build();
    }
}
