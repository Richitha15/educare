package com.educare.repository;

import com.educare.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByScope(Report.ReportScope scope);
    List<Report> findByGeneratedByUserId(Long userId);
}
