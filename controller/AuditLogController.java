package com.educare.controller;

import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.AuditLogResponse;
import com.educare.repository.AuditLogRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "Append-only audit trail — Admin and Auditor only")
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    @GetMapping
    @Operation(summary = "Get latest 50 audit log entries")
    public ResponseEntity<ApiResponse<List<AuditLogResponse>>> getRecent() {
        List<AuditLogResponse> logs = auditLogRepository.findTop50ByOrderByTimestampDesc()
            .stream().map(AuditLogResponse::from).toList();
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get audit logs for a specific user")
    public ResponseEntity<ApiResponse<List<AuditLogResponse>>> getByUser(@PathVariable Long userId) {
        List<AuditLogResponse> logs = auditLogRepository.findByUserUserId(userId)
            .stream().map(AuditLogResponse::from).toList();
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/resource/{resourceType}")
    @Operation(summary = "Get audit logs by resource type")
    public ResponseEntity<ApiResponse<List<AuditLogResponse>>> getByResourceType(@PathVariable String resourceType) {
        List<AuditLogResponse> logs = auditLogRepository.findByResourceType(resourceType)
            .stream().map(AuditLogResponse::from).toList();
        return ResponseEntity.ok(ApiResponse.success(logs));
    }
}
