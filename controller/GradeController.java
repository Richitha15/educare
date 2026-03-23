package com.educare.controller;

import com.educare.dto.request.GradeRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.GradeResponse;
import com.educare.model.Grade;
import com.educare.model.User;
import com.educare.service.GradeService;
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
@RequestMapping("/api/grades")
@RequiredArgsConstructor
@Tag(name = "Grades", description = "Grade submission and management")
public class GradeController {

    private final GradeService gradeService;

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all grades for a student")
    public ResponseEntity<ApiResponse<List<GradeResponse>>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(gradeService.getGradesByStudent(studentId)));
    }

    @GetMapping("/student/{studentId}/average")
    @Operation(summary = "Get average grade for a student")
    public ResponseEntity<ApiResponse<Double>> getAverage(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(gradeService.getStudentAverage(studentId)));
    }

    @GetMapping("/exam/{examId}")
    @Operation(summary = "Get all grades for an exam")
    public ResponseEntity<ApiResponse<List<GradeResponse>>> getByExam(@PathVariable Long examId) {
        return ResponseEntity.ok(ApiResponse.success(gradeService.getGradesByExam(examId)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get grade by ID")
    public ResponseEntity<ApiResponse<GradeResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(gradeService.getGradeById(id)));
    }

    @PostMapping
    @Operation(summary = "Submit a grade")
    public ResponseEntity<ApiResponse<GradeResponse>> submit(
            @Valid @RequestBody GradeRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Grade submitted", gradeService.submitGrade(request, currentUser)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update grade status (DRAFT / SUBMITTED / MODERATED / PUBLISHED)")
    public ResponseEntity<ApiResponse<GradeResponse>> updateStatus(
            @PathVariable Long id, @RequestParam Grade.GradeStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Grade status updated", gradeService.updateGradeStatus(id, status)));
    }
}
