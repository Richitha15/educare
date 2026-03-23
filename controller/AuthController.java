package com.educare.controller;

import com.educare.dto.request.LoginRequest;
import com.educare.dto.request.RegisterRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.AuthResponse;
import com.educare.model.User;
import com.educare.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Login, register, and get current user")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate and receive a JWT token")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", authService.login(request)));
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Create a new user account (Admin only in production)")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Registration successful", authService.register(request)));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns the currently authenticated user's profile")
    public ResponseEntity<ApiResponse<User>> me(@AuthenticationPrincipal User user) {
        user.setPassword(null); // never expose password
        return ResponseEntity.ok(ApiResponse.success(user));
    }
}
