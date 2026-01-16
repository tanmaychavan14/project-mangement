package com.example.collegeprojectm.dtoo;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkPlanDto {
    private Long id;
    private Integer studentYear;
    private String batch;
    private LocalDate workDate;
    private String expectedWork;
}
