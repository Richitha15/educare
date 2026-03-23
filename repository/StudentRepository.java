package com.educare.repository;

import com.educare.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByStatus(Student.StudentStatus status);
    List<Student> findByProgram(String program);
    List<Student> findByNameContainingIgnoreCase(String name);
}
