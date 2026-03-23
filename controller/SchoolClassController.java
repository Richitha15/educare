package com.educare.controller;

import com.educare.dto.request.SchoolClassRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.SchoolClassResponse;
import com.educare.service.SchoolClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
@Tag(name = "Classes", description = "Class scheduling and management")
public class SchoolClassController {

    private final SchoolClassService classService;

    @GetMapping
    @Operation(summary = "Get all classes")
    public ResponseEntity<ApiResponse<List<SchoolClassResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(classService.getAllClasses()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get class by ID")
    public ResponseEntity<ApiResponse<SchoolClassResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(classService.getClassById(id)));
    }

    @PostMapping
    @Operation(summary = "Create new class")
    public ResponseEntity<ApiResponse<SchoolClassResponse>> create(@Valid @RequestBody SchoolClassRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Class created", classService.createClass(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update class")
    public ResponseEntity<ApiResponse<SchoolClassResponse>> update(
            @PathVariable Long id, @Valid @RequestBody SchoolClassRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Class updated", classService.updateClass(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Archive class")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.ok(ApiResponse.success("Class archived", null));
    }
}
