package com.example.collegeprojectm.dtoo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateWeeklyReportRequest {


    private Long groupId;


    private Integer studentYear;

    @NotNull
    private LocalDate workDate;

    @NotNull
    private Long workPlanId;

    private String workDone;
    private String guideFeedback;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer progressPercentage;
}
