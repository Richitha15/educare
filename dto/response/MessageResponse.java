package com.educare.dto.response;

import com.educare.model.Message;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long messageId;
    private Long fromUserId;
    private String fromUserName;
    private Long toUserId;
    private String toUserName;
    private String content;
    private Message.MessageStatus status;
    private LocalDateTime sentAt;

    public static MessageResponse from(Message m) {
        return MessageResponse.builder()
            .messageId(m.getMessageId())
            .fromUserId(m.getFromUser() != null ? m.getFromUser().getUserId() : null)
            .fromUserName(m.getFromUser() != null ? m.getFromUser().getName() : null)
            .toUserId(m.getToUser() != null ? m.getToUser().getUserId() : null)
            .toUserName(m.getToUser() != null ? m.getToUser().getName() : null)
            .content(m.getContent())
            .status(m.getStatus())
            .sentAt(m.getSentAt())
            .build();
    }
}
