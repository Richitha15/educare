package com.educare.dto.response;

import com.educare.model.AuditLog;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogResponse {
    private Long auditId;
    private Long userId;
    private String userName;
    private String action;
    private String resourceType;
    private Long resourceId;
    private String details;
    private LocalDateTime timestamp;

    public static AuditLogResponse from(AuditLog a) {
        return AuditLogResponse.builder()
            .auditId(a.getAuditId())
            .userId(a.getUser() != null ? a.getUser().getUserId() : null)
            .userName(a.getUser() != null ? a.getUser().getName() : null)
            .action(a.getAction())
            .resourceType(a.getResourceType())
            .resourceId(a.getResourceId())
            .details(a.getDetails())
            .timestamp(a.getTimestamp())
            .build();
    }
}
