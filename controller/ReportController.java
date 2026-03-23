package com.educare.controller;

import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.ReportResponse;
import com.educare.model.Report;
import com.educare.model.User;
import com.educare.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Academic reports and KPI tracking")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @Operation(summary = "Get all reports")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> getAll(
            @RequestParam(required = false) Report.ReportScope scope) {
        List<ReportResponse> result = (scope != null)
            ? reportService.getReportsByScope(scope)
            : reportService.getAllReports();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get report by ID")
    public ResponseEntity<ApiResponse<ReportResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getReportById(id)));
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate a new report")
    public ResponseEntity<ApiResponse<ReportResponse>> generate(
            @RequestParam Report.ReportScope scope,
            @RequestParam(required = false) String parametersJson,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Report generated",
                reportService.generateReport(scope, parametersJson, currentUser)));
    }
}
