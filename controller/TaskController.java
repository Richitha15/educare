package com.educare.controller;

import com.educare.dto.request.TaskRequest;
import com.educare.dto.response.ApiResponse;
import com.educare.dto.response.TaskResponse;
import com.educare.model.Task;
import com.educare.model.User;
import com.educare.service.TaskService;
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
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task assignment and alert management")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/my")
    @Operation(summary = "Get all tasks assigned to current user")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getMy(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ApiResponse.success(taskService.getTasksByUser(currentUser.getUserId())));
    }

    @GetMapping("/my/pending")
    @Operation(summary = "Get pending tasks for current user")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getMyPending(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ApiResponse.success(taskService.getMyPendingTasks(currentUser.getUserId())));
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get all overdue tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getOverdue() {
        return ResponseEntity.ok(ApiResponse.success(taskService.getOverdueTasks()));
    }

    @PostMapping
    @Operation(summary = "Create and assign a new task")
    public ResponseEntity<ApiResponse<TaskResponse>> create(@Valid @RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Task created", taskService.createTask(request)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update task status")
    public ResponseEntity<ApiResponse<TaskResponse>> updateStatus(
            @PathVariable Long id, @RequestParam Task.TaskStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Task updated", taskService.updateTaskStatus(id, status)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.success("Task deleted", null));
    }
}
