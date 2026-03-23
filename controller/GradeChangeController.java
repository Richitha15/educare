package com.educare.controller;

import com.educare.dto.request.GradeChangeRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.GradeChangeResponse;
import com.educare.model.GradeChange;
import com.educare.model.User;
import com.educare.service.GradeChangeService;
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
@RequestMapping("/api/grade-changes")
@RequiredArgsConstructor
@Tag(name = "Grade Changes", description = "Grade change requests with evidence")
public class GradeChangeController {

    private final GradeChangeService gradeChangeService;

    @GetMapping("/pending")
    @Operation(summary = "Get all pending grade change requests")
    public ResponseEntity<ApiResponse<List<GradeChangeResponse>>> getPending() {
        return ResponseEntity.ok(ApiResponse.success(gradeChangeService.getPendingChanges()));
    }

    @GetMapping("/grade/{gradeId}")
    @Operation(summary = "Get change history for a specific grade")
    public ResponseEntity<ApiResponse<List<GradeChangeResponse>>> getByGrade(@PathVariable Long gradeId) {
        return ResponseEntity.ok(ApiResponse.success(gradeChangeService.getChangesByGrade(gradeId)));
    }

    @PostMapping
    @Operation(summary = "Submit a grade change request")
    public ResponseEntity<ApiResponse<GradeChangeResponse>> request(
            @Valid @RequestBody GradeChangeRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Change request submitted",
                gradeChangeService.requestChange(request, currentUser)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Approve or reject a grade change request")
    public ResponseEntity<ApiResponse<GradeChangeResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam GradeChange.ChangeStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated",
            gradeChangeService.updateChangeStatus(id, status)));
    }
}
