package com.educare.dto.response;

import com.educare.model.KPI;
import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KPIResponse {
    private Long kpiId;
    private String name;
    private String definition;
    private BigDecimal target;
    private BigDecimal currentValue;
    private String reportingPeriod;
    private String performance;

    public static KPIResponse from(KPI k) {
        String performance = "N/A";
        if (k.getTarget() != null && k.getCurrentValue() != null) {
            performance = k.getCurrentValue().compareTo(k.getTarget()) >= 0
                ? "ON TARGET" : "BELOW TARGET";
        }
        return KPIResponse.builder()
            .kpiId(k.getKpiId())
            .name(k.getName())
            .definition(k.getDefinition())
            .target(k.getTarget())
            .currentValue(k.getCurrentValue())
            .reportingPeriod(k.getReportingPeriod())
            .performance(performance)
            .build();
    }
}
