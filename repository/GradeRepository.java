package com.educare.repository;

import com.educare.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentStudentId(Long studentId);
    List<Grade> findByExamExamId(Long examId);
    List<Grade> findByStatus(Grade.GradeStatus status);
    Optional<Grade> findByStudentStudentIdAndExamExamId(Long studentId, Long examId);

    @Query("SELECT AVG(g.gradeValue) FROM Grade g WHERE g.student.studentId = :studentId AND g.status = 'PUBLISHED'")
    Double findAverageGradeByStudentId(@Param("studentId") Long studentId);
}
