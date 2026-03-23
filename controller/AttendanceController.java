package com.educare.controller;

import com.educare.dto.request.AttendanceRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.AttendanceResponse;
import com.educare.model.AttendanceRecord;
import com.educare.model.User;
import com.educare.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "Student attendance tracking")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @Operation(summary = "Mark attendance for a student")
    public ResponseEntity<ApiResponse<AttendanceResponse>> mark(
            @Valid @RequestBody AttendanceRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Attendance marked", attendanceService.markAttendance(request, currentUser)));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all attendance records for a student")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(attendanceService.getByStudent(studentId)));
    }

    @GetMapping("/student/{studentId}/range")
    @Operation(summary = "Get attendance for a student within a date range")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getByStudentDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(ApiResponse.success(attendanceService.getByStudentDateRange(studentId, from, to)));
    }

    @GetMapping("/student/{studentId}/summary")
    @Operation(summary = "Get attendance summary (present/absent counts) for a student")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getSummary(@PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(attendanceService.getAttendanceSummary(studentId)));
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "Get attendance for a class on a specific date")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getByClassAndDate(
            @PathVariable Long classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ApiResponse.success(attendanceService.getByClassAndDate(classId, date)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update an attendance record status")
    public ResponseEntity<ApiResponse<AttendanceResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam AttendanceRecord.AttendanceStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Attendance updated", attendanceService.updateAttendance(id, status)));
    }
}
