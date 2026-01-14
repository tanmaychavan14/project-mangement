package com.example.collegeprojectm.repository;

import com.example.collegeprojectm.model.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentGroupRepository extends JpaRepository<StudentGroup,Long> {

    Optional<StudentGroup> findByGroupNameAndBatchAndCurrentYear(String groupName, String batch, Integer currentYear);
    Boolean existsByGroupNameAndBatchAndCurrentYear(String groupName, String batch, Integer currentYear);
}
