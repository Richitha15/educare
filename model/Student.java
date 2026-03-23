package com.educare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "dob")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "contact_info", columnDefinition = "TEXT")
    private String contactInfo;

    @Column(length = 100)
    private String program;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StudentStatus status = StudentStatus.ACTIVE;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum StudentStatus {
        ACTIVE, INACTIVE, GRADUATED, EXPELLED
    }
}
