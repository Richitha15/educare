package com.educare.dto.response;

import com.educare.model.GradeChange;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeChangeResponse {
    private Long changeId;
    private Long gradeId;
    private String changedByName;
    private LocalDateTime changedAt;
    private String reason;
    private String evidenceUri;
    private GradeChange.ChangeStatus status;

    public static GradeChangeResponse from(GradeChange gc) {
        return GradeChangeResponse.builder()
            .changeId(gc.getChangeId())
            .gradeId(gc.getGrade().getGradeId())
            .changedByName(gc.getChangedBy() != null ? gc.getChangedBy().getName() : null)
            .changedAt(gc.getChangedAt())
            .reason(gc.getReason())
            .evidenceUri(gc.getEvidenceUri())
            .status(gc.getStatus())
            .build();
    }
}
