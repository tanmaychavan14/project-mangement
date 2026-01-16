package com.example.collegeprojectm.dtoo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateWeeklyReportRequest {

    private String workDone;
    private String guideFeedback;

    @Min(0)
    @Max(100)
    private Integer progressPercentage;
}
