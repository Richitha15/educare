package com.educare.dto.response;

import com.educare.model.Notification;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Long notificationId;
    private Long userId;
    private String userName;
    private Long entityId;
    private String message;
    private Notification.NotificationCategory category;
    private Notification.NotificationStatus status;
    private LocalDateTime createdAt;

    public static NotificationResponse from(Notification n) {
        return NotificationResponse.builder()
            .notificationId(n.getNotificationId())
            .userId(n.getUser().getUserId())
            .userName(n.getUser().getName())
            .entityId(n.getEntityId())
            .message(n.getMessage())
            .category(n.getCategory())
            .status(n.getStatus())
            .createdAt(n.getCreatedAt())
            .build();
    }
}
