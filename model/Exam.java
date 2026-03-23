package com.educare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Long examId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(nullable = false, length = 100)
    private String subject;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ExamStatus status = ExamStatus.SCHEDULED;

    public enum ExamStatus {
        SCHEDULED, ONGOING, COMPLETED, CANCELLED
    }
}
