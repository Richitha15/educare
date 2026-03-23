package com.educare.dto.response;

import com.educare.model.User;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String name;
    private User.Role role;
    private String email;
    private String phone;
    private User.UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse from(User u) {
        return UserResponse.builder()
            .userId(u.getUserId())
            .name(u.getName())
            .role(u.getRole())
            .email(u.getEmail())
            .phone(u.getPhone())
            .status(u.getStatus())
            .createdAt(u.getCreatedAt())
            .updatedAt(u.getUpdatedAt())
            .build();
    }
}
