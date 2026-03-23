package com.educare.repository;

import com.educare.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByStudentStudentId(Long studentId);
    List<AttendanceRecord> findBySchoolClassClassId(Long classId);
    List<AttendanceRecord> findBySchoolClassClassIdAndDate(Long classId, LocalDate date);
    List<AttendanceRecord> findByStudentStudentIdAndDateBetween(Long studentId, LocalDate from, LocalDate to);

    @Query("SELECT COUNT(a) FROM AttendanceRecord a WHERE a.student.studentId = :studentId AND a.status = 'PRESENT'")
    long countPresentByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(a) FROM AttendanceRecord a WHERE a.student.studentId = :studentId AND a.status = 'ABSENT'")
    long countAbsentByStudentId(@Param("studentId") Long studentId);
}
