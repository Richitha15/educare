package com.educare.dto.response;

import com.educare.model.SchoolClass;
import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class SchoolClassResponse {
    private Long classId;
    private String name;
    private String gradeLevel;
    private String scheduleJson;
    private Integer capacity;
    private SchoolClass.ClassStatus status;

    public static SchoolClassResponse from(SchoolClass c) {
        return SchoolClassResponse.builder()
            .classId(c.getClassId()).name(c.getName())
            .gradeLevel(c.getGradeLevel()).scheduleJson(c.getScheduleJson())
            .capacity(c.getCapacity()).status(c.getStatus()).build();
    }
}
