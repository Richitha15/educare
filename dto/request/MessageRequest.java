package com.educare.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequest {
    @NotNull(message = "Recipient user ID is required")
    private Long toUserId;

    @NotBlank(message = "Content is required")
    private String content;
}
