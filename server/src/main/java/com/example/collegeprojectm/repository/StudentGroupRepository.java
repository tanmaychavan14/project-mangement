package com.example.collegeprojectm.repository;

import com.example.collegeprojectm.model.StudentGroup;
import com.example.collegeprojectm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
public interface StudentGroupRepository extends JpaRepository<StudentGroup,Long> {

    Optional<StudentGroup> findByGroupNameAndBatchAndCurrentYear(String groupName, String batch, Integer currentYear);
    Boolean existsByGroupNameAndBatchAndCurrentYear(String groupName, String batch, Integer currentYear);
    List<StudentGroup> findByBatchAndCurrentYear(
            String batch,
            Integer currentYear
    );
    List<StudentGroup> findByGuideIdAndBatchAndCurrentYear(
            User guide,
            String batch,
            Integer currentYear
    );

}
