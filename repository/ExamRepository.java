package com.educare.repository;

import com.educare.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findBySchoolClassClassId(Long classId);
    List<Exam> findByStatus(Exam.ExamStatus status);
    List<Exam> findBySubjectContainingIgnoreCase(String subject);
}
