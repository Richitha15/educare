package com.educare.controller;

import com.educare.dto.request.EnrollmentRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.EnrollmentResponse;
import com.educare.model.EnrollmentRecord;
import com.educare.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollments", description = "Student enrollment management")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    @Operation(summary = "Get all enrollments")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(enrollmentService.getAllEnrollments()));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get enrollments by student")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(enrollmentService.getEnrollmentsByStudent(studentId)));
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "Get enrollments by class")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(ApiResponse.success(enrollmentService.getEnrollmentsByClass(classId)));
    }

    @PostMapping
    @Operation(summary = "Enroll student in class")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> enroll(@Valid @RequestBody EnrollmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Student enrolled", enrollmentService.createEnrollment(request)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update enrollment status (ENROLLED / DROPPED / COMPLETED)")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam EnrollmentRecord.EnrollStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", enrollmentService.updateStatus(id, status)));
    }
}
