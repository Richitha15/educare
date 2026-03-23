package com.educare.repository;

import com.educare.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToUserId(Long userId);
    List<Task> findByStatus(Task.TaskStatus status);
    List<Task> findByAssignedToUserIdAndStatus(Long userId, Task.TaskStatus status);
    List<Task> findByDueDateBeforeAndStatusNot(LocalDate date, Task.TaskStatus status);
}
