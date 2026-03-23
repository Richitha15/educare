package com.educare.controller;

import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.NotificationResponse;
import com.educare.dto.request.NotificationRequest;
import com.educare.model.User;
import com.educare.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "In-app notifications for all users")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/my")
    @Operation(summary = "Get all notifications for the current user")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getMy(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.getByUser(currentUser.getUserId())));
    }

    @GetMapping("/my/unread")
    @Operation(summary = "Get unread notifications for the current user")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUnread(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.getUnreadByUser(currentUser.getUserId())));
    }

    @GetMapping("/my/unread-count")
    @Operation(summary = "Get count of unread notifications")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.countUnread(currentUser.getUserId())));
    }

    @PostMapping
    @Operation(summary = "Create a notification")
    public ResponseEntity<ApiResponse<NotificationResponse>> create(@Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Notification created", notificationService.createNotification(request)));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark a notification as read")
    public ResponseEntity<ApiResponse<NotificationResponse>> markRead(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Marked as read", notificationService.markAsRead(id)));
    }

    @PutMapping("/my/read-all")
    @Operation(summary = "Mark all notifications as read")
    public ResponseEntity<ApiResponse<Void>> markAllRead(@AuthenticationPrincipal User currentUser) {
        notificationService.markAllAsRead(currentUser.getUserId());
        return ResponseEntity.ok(ApiResponse.success("All marked as read", null));
    }
}
