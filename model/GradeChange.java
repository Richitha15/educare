package com.educare.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "grade_changes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "change_id")
    private Long changeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @CreationTimestamp
    @Column(name = "changed_at", updatable = false)
    private LocalDateTime changedAt;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "evidence_uri", length = 500)
    private String evidenceUri;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ChangeStatus status = ChangeStatus.PENDING;

    public enum ChangeStatus {
        PENDING, APPROVED, REJECTED
    }
}
