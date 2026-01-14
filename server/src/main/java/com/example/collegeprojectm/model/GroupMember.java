package com.example.collegeprojectm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "group_members",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "current_year"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private StudentGroup group;



    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User studentId;

    @Column(name = "current_year", nullable = false)
    private Integer currentYear;

    @Column(nullable = false)
    private boolean leader;
}
