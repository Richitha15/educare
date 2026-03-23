package com.educare.repository;

import com.educare.model.EnrollmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRecordRepository extends JpaRepository<EnrollmentRecord, Long> {
    List<EnrollmentRecord> findByStudentStudentId(Long studentId);
    List<EnrollmentRecord> findBySchoolClassClassId(Long classId);
    List<EnrollmentRecord> findByStatus(EnrollmentRecord.EnrollStatus status);
    Optional<EnrollmentRecord> findByStudentStudentIdAndSchoolClassClassId(Long studentId, Long classId);
    long countBySchoolClassClassIdAndStatus(Long classId, EnrollmentRecord.EnrollStatus status);
}
