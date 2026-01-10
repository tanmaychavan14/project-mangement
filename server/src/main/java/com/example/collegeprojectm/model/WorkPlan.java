package com.example.collegeprojectm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "work_plan",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_year", "work_date"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_year", nullable = false)
    private Integer studentYear;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name ="expected_work",columnDefinition = "TEXT", nullable = false)
    private String expectedWork;
}
