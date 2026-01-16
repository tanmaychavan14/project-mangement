package com.example.collegeprojectm.repository;

import com.example.collegeprojectm.model.WorkPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

public interface WorkPlanRepository extends JpaRepository<WorkPlan, Long> {

    List<WorkPlan> findByStudentYearAndBatch(Integer studentYear, String batch);
    Optional<WorkPlan> findByStudentYearAndWorkDate(Integer studentYear, LocalDate workDate);
    boolean existsByStudentYearAndBatchAndWorkDate(
            Integer studentYear,
            String batch,
            LocalDate workDate
    );
    Optional<WorkPlan> findByStudentYearAndBatchAndWorkDate(
            Integer studentYear,
            String batch,
            LocalDate workDate
    );
}
