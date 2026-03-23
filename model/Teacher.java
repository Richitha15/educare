package com.educare.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 100)
    private String department;

    @Column(name = "contact_info", columnDefinition = "TEXT")
    private String contactInfo;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TeacherStatus status = TeacherStatus.ACTIVE;

    public enum TeacherStatus {
        ACTIVE, INACTIVE, ON_LEAVE
    }
}
