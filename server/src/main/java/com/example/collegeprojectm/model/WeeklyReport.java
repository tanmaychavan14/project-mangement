package com.example.collegeprojectm.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.time.LocalDate;

@Entity
@Table(
        name = "weekly_reports",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"group_id", "student_year", "work_date"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private StudentGroup groupId;

    @Column(name = "student_year", nullable = false)
    private Integer studentYear;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @ManyToOne
    @JoinColumn(name = "work_plan_id", nullable = false)
    private WorkPlan workPlanId;

    @Column(columnDefinition = "TEXT")
    private String workDone;

    @Column(columnDefinition = "TEXT")
    private String guideFeedback;

    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private Integer progressPercentage;
}
