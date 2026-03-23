package com.educare.dto.response;

import com.educare.model.Report;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private Long reportId;
    private Report.ReportScope scope;
    private String parametersJson;
    private String metricsJson;
    private Long generatedByUserId;
    private String generatedByName;
    private LocalDateTime generatedAt;
    private String reportUri;

    public static ReportResponse from(Report r) {
        return ReportResponse.builder()
            .reportId(r.getReportId())
            .scope(r.getScope())
            .parametersJson(r.getParametersJson())
            .metricsJson(r.getMetricsJson())
            .generatedByUserId(r.getGeneratedBy() != null ? r.getGeneratedBy().getUserId() : null)
            .generatedByName(r.getGeneratedBy() != null ? r.getGeneratedBy().getName() : null)
            .generatedAt(r.getGeneratedAt())
            .reportUri(r.getReportUri())
            .build();
    }
}
