package com.educare.controller;

import com.educare.dto.request.StudentRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.StudentResponse;
import com.educare.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Students", description = "Student registry and management")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Get all students")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAll(
            @RequestParam(required = false) String search) {
        List<StudentResponse> result = (search != null && !search.isBlank())
            ? studentService.searchStudents(search)
            : studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<ApiResponse<StudentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(studentService.getStudentById(id)));
    }

    @PostMapping
    @Operation(summary = "Create new student")
    public ResponseEntity<ApiResponse<StudentResponse>> create(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Student created", studentService.createStudent(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    public ResponseEntity<ApiResponse<StudentResponse>> update(
            @PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Student updated", studentService.updateStudent(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate student")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student deactivated", null));
    }
}
