package com.educare.controller;

import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.UserResponse;
import com.educare.model.User;
import com.educare.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management — Admin only")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAll(
            @RequestParam(required = false) User.Role role) {
        List<UserResponse> users = (role != null)
            ? userService.getUsersByRole(role)
            : userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUserById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user name and phone")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone) {
        return ResponseEntity.ok(ApiResponse.success("User updated", userService.updateUser(id, name, phone)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Activate, deactivate, or suspend a user")
    public ResponseEntity<ApiResponse<UserResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam User.UserStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", userService.updateUserStatus(id, status)));
    }
}
