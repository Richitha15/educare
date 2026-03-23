package com.educare.controller;

import com.educare.dto.request.MessageRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.MessageResponse;
import com.educare.model.User;
import com.educare.service.MessageService;
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
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Direct messaging between users")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/inbox")
    @Operation(summary = "Get all messages for current user")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getInbox(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ApiResponse.success(messageService.getInbox(currentUser.getUserId())));
    }

    @GetMapping("/conversation/{otherUserId}")
    @Operation(summary = "Get conversation between current user and another user")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getConversation(
            @PathVariable Long otherUserId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ApiResponse.success(
            messageService.getConversation(currentUser.getUserId(), otherUserId)));
    }

    @PostMapping
    @Operation(summary = "Send a message")
    public ResponseEntity<ApiResponse<MessageResponse>> send(
            @Valid @RequestBody MessageRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Message sent", messageService.sendMessage(request, currentUser)));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark message as read")
    public ResponseEntity<ApiResponse<MessageResponse>> markRead(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Message read", messageService.markAsRead(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a message")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok(ApiResponse.success("Message deleted", null));
    }
}
