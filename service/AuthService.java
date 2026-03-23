package com.educare.service;

import com.educare.dto.request.LoginRequest;
import com.educare.dto.request.RegisterRequest;
import com.educare.dto.response.AuthResponse;
import com.educare.exception.BadRequestException;
import com.educare.exception.ResourceNotFoundException;
import com.educare.model.AuditLog;
import com.educare.model.User;
import com.educare.repository.AuditLogRepository;
import com.educare.repository.UserRepository;
import com.educare.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new BadRequestException("Account is " + user.getStatus().name().toLowerCase() + ". Contact administrator.");
        }

        // Append-only audit log entry
        auditLogRepository.save(AuditLog.builder()
            .user(user)
            .action("LOGIN")
            .resourceType("AUTH")
            .details("User logged in successfully")
            .build());

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
            .token(token)
            .type("Bearer")
            .userId(user.getUserId())
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered: " + request.getEmail());
        }

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .phone(request.getPhone())
            .status(User.UserStatus.ACTIVE)
            .build();

        User saved = userRepository.save(user);

        auditLogRepository.save(AuditLog.builder()
            .user(saved)
            .action("REGISTER")
            .resourceType("USER")
            .resourceId(saved.getUserId())
            .details("New user registered with role: " + saved.getRole())
            .build());

        String token = jwtUtil.generateToken(saved);

        return AuthResponse.builder()
            .token(token)
            .type("Bearer")
            .userId(saved.getUserId())
            .name(saved.getName())
            .email(saved.getEmail())
            .role(saved.getRole())
            .build();
    }
}
