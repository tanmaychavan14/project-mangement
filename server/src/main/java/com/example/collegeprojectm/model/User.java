package com.example.collegeprojectm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"roll_number","batch"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roll_number", nullable = false)
    private String rollNumber;   // used by students

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
    // STUDENT / FACULTY / ADMIN

    @Column
    private String batch;
    // graduation / pass-out year (students only 2022-2026)

    @Column(name = "current_year", nullable = false)
    private Integer currentYear;
    // 1, 2, 3, 4 (admin increments)

    @Column(nullable = false)
    private String status;
    // ACTIVE / GRADUATED
}
