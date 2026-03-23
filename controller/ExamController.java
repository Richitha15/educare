package com.educare.controller;

import com.educare.dto.request.ExamRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.ExamResponse;
import com.educare.model.Exam;
import com.educare.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@Tag(name = "Exams", description = "Exam scheduling and management")
public class ExamController {

    private final ExamService examService;

    @GetMapping
    @Operation(summary = "Get all exams")
    public ResponseEntity<ApiResponse<List<ExamResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(examService.getAllExams()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get exam by ID")
    public ResponseEntity<ApiResponse<ExamResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(examService.getExamById(id)));
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "Get all exams for a class")
    public ResponseEntity<ApiResponse<List<ExamResponse>>> getByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(ApiResponse.success(examService.getExamsByClass(classId)));
    }

    @PostMapping
    @Operation(summary = "Schedule a new exam")
    public ResponseEntity<ApiResponse<ExamResponse>> create(@Valid @RequestBody ExamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Exam scheduled", examService.createExam(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update exam details")
    public ResponseEntity<ApiResponse<ExamResponse>> update(
            @PathVariable Long id, @Valid @RequestBody ExamRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Exam updated", examService.updateExam(id, request)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update exam status (SCHEDULED / ONGOING / COMPLETED / CANCELLED)")
    public ResponseEntity<ApiResponse<ExamResponse>> updateStatus(
            @PathVariable Long id, @RequestParam Exam.ExamStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", examService.updateStatus(id, status)));
    }
}
