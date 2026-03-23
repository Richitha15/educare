package com.educare.repository;

import com.educare.model.AuditPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuditPackageRepository extends JpaRepository<AuditPackage, Long> {
    List<AuditPackage> findByPeriodStartGreaterThanEqual(LocalDate from);
    List<AuditPackage> findByPeriodStartBetween(LocalDate from, LocalDate to);
}
