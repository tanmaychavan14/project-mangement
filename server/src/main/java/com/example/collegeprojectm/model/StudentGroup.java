package com.example.collegeprojectm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_groups",
      uniqueConstraints = {
        @UniqueConstraint(columnNames = {"groupName",  "batch", "currentYear"})
      })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // this is the GROUP ID given by admin

    @Column(name = "group_name")
    private String groupName;


    @Column(nullable = false)
    private String batch;

    @Column(name = "project_Title", nullable = false)
    private String projectTitle;

    @Column(name = "project_domain", nullable = false)
    private String projectDomain;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private User guideId;
    // optional, assigned by admin

    @Column(name = "current_year" , nullable = false)
    private Integer currentYear;
    // matches user.currentYear

    @Column(nullable = false)
    private String status;
    // ACTIVE / COMPLETED
}
