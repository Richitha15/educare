package com.educare.dto.response;

import com.educare.model.AttendanceRecord;
import lombok.*;
import java.time.LocalDate;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class AttendanceResponse {
    private Long attendanceId;
    private Long studentId;
    private String studentName;
    private Long classId;
    private String className;
    private LocalDate date;
    private AttendanceRecord.AttendanceStatus status;
    private String recordedByName;

    public static AttendanceResponse from(AttendanceRecord a) {
        return AttendanceResponse.builder()
            .attendanceId(a.getAttendanceId())
            .studentId(a.getStudent().getStudentId())
            .studentName(a.getStudent().getName())
            .classId(a.getSchoolClass().getClassId())
            .className(a.getSchoolClass().getName())
            .date(a.getDate())
            .status(a.getStatus())
            .recordedByName(a.getRecordedBy() != null ? a.getRecordedBy().getName() : null)
            .build();
    }
}
