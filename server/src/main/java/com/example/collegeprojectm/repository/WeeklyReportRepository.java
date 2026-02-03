package com.example.collegeprojectm.repository;

import com.example.collegeprojectm.model.WeeklyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Long> {

    Optional<WeeklyReport> findByGroupId_IdAndStudentYearAndWorkDate(
            Long groupId,
            Integer studentYear,
            LocalDate workDate
    );

    // 🔹 batch + year
    List<WeeklyReport> findByGroupId_BatchAndStudentYear(
            String batch,
            Integer studentYear
    );

    // 🔹 batch + groupName + year
    List<WeeklyReport> findByGroupId_GroupNameAndGroupId_BatchAndStudentYear(
            String groupName,
            String batch,
            Integer studentYear
    );
    List<WeeklyReport> findByGroupId_IdAndStudentYear(
            Long groupId,
            Integer studentYear
    );
    List<WeeklyReport> findByGroupId_Id(Long groupId);

}
