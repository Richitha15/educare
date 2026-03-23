package com.educare.repository;

import com.educare.model.GradeChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GradeChangeRepository extends JpaRepository<GradeChange, Long> {
    List<GradeChange> findByGradeGradeId(Long gradeId);
    List<GradeChange> findByStatus(GradeChange.ChangeStatus status);
    List<GradeChange> findByChangedByUserId(Long userId);
}
