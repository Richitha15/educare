package com.educare.controller;

import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.KPIResponse;
import com.educare.service.KPIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/kpis")
@RequiredArgsConstructor
@Tag(name = "KPIs", description = "Key Performance Indicators — Admin and Auditor only")
public class KPIController {

    private final KPIService kpiService;

    @GetMapping
    @Operation(summary = "Get all KPIs")
    public ResponseEntity<ApiResponse<List<KPIResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(kpiService.getAllKPIs()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get KPI by ID")
    public ResponseEntity<ApiResponse<KPIResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(kpiService.getKPIById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new KPI")
    public ResponseEntity<ApiResponse<KPIResponse>> create(
            @RequestParam String name,
            @RequestParam(required = false) String definition,
            @RequestParam BigDecimal target,
            @RequestParam String reportingPeriod) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("KPI created",
                kpiService.createKPI(name, definition, target, reportingPeriod)));
    }

    @PutMapping("/{id}/value")
    @Operation(summary = "Update current value of a KPI")
    public ResponseEntity<ApiResponse<KPIResponse>> updateValue(
            @PathVariable Long id,
            @RequestParam BigDecimal currentValue) {
        return ResponseEntity.ok(ApiResponse.success("KPI updated",
            kpiService.updateCurrentValue(id, currentValue)));
    }
}
