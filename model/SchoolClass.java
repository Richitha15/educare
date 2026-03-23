package com.educare.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "grade_level", length = 50)
    private String gradeLevel;

    @Column(name = "schedule_json", columnDefinition = "TEXT")
    private String scheduleJson;

    @Builder.Default
    private Integer capacity = 30;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ClassStatus status = ClassStatus.ACTIVE;

    public enum ClassStatus {
        ACTIVE, INACTIVE, ARCHIVED
    }
}
