package com.educare.repository;

import com.educare.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUserUserId(Long userId);
    List<AuditLog> findByResourceType(String resourceType);
    List<AuditLog> findByAction(String action);
    List<AuditLog> findTop50ByOrderByTimestampDesc();
}
