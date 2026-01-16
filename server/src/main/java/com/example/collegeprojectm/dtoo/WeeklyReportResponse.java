package com.example.collegeprojectm.dtoo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeeklyReportResponse {

    private Long id;
    private String groupName;
    private String batch;
    private Integer studentYear;
    private LocalDate workDate;
    private String expectedWork;
    private String workDone;
    private String guideFeedback;
    private Integer progressPercentage;
}
