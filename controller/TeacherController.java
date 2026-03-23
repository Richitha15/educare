package com.educare.controller;

import com.educare.dto.request.TeacherAssignmentRequest;
import com.educare.dto.request.TeacherRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.TeacherAssignmentResponse;
import com.educare.dto.response.TeacherResponse;
import com.educare.model.User;
import com.educare.service.TeacherService;
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
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Tag(name = "Teachers", description = "Teacher management and class assignments")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    @Operation(summary = "Get all teachers")
    public ResponseEntity<ApiResponse<List<TeacherResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getAllTeachers()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get teacher by ID")
    public ResponseEntity<ApiResponse<TeacherResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getTeacherById(id)));
    }

    @PostMapping
    @Operation(summary = "Create new teacher")
    public ResponseEntity<ApiResponse<TeacherResponse>> create(@Valid @RequestBody TeacherRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Teacher created", teacherService.createTeacher(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update teacher")
    public ResponseEntity<ApiResponse<TeacherResponse>> update(
            @PathVariable Long id, @Valid @RequestBody TeacherRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Teacher updated",
            teacherService.updateTeacher(id, request)));
    }

    @PostMapping("/assign")
    @Operation(summary = "Assign teacher to a class")
    public ResponseEntity<ApiResponse<TeacherAssignmentResponse>> assign(
            @Valid @RequestBody TeacherAssignmentRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Teacher assigned",
                teacherService.assignTeacherToClass(request, currentUser.getUserId())));
    }

    @GetMapping("/assignments/class/{classId}")
    @Operation(summary = "Get teacher assignments for a class")
    public ResponseEntity<ApiResponse<List<TeacherAssignmentResponse>>> getByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getAssignmentsByClass(classId)));
    }

    @GetMapping("/{teacherId}/assignments")
    @Operation(summary = "Get all assignments for a teacher")
    public ResponseEntity<ApiResponse<List<TeacherAssignmentResponse>>> getByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getAssignmentsByTeacher(teacherId)));
    }
}
