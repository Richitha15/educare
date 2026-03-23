package com.educare.repository;

import com.educare.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findByStatus(SchoolClass.ClassStatus status);
    List<SchoolClass> findByGradeLevel(String gradeLevel);
    List<SchoolClass> findByNameContainingIgnoreCase(String name);
}
