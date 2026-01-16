package com.example.collegeprojectm.controller;

import com.example.collegeprojectm.dtoo.CreateWeeklyReportRequest;
import com.example.collegeprojectm.dtoo.UpdateWeeklyReportRequest;
import com.example.collegeprojectm.dtoo.WeeklyReportResponse;
import com.example.collegeprojectm.service.WeeklyReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-reports")
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    public WeeklyReportController(WeeklyReportService weeklyReportService){
        this.weeklyReportService=weeklyReportService;
    }
    /* ===== CREATE ===== */
    @PostMapping("/create-report")
    public ResponseEntity<WeeklyReportResponse> create(
            @Valid @RequestBody CreateWeeklyReportRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(weeklyReportService.create(request));
    }

    @PostMapping("/student/create-report")
    public ResponseEntity<WeeklyReportResponse> createByStudent(
            @RequestParam String rollNumber,
            @Valid @RequestBody CreateWeeklyReportRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(weeklyReportService.createByRollNumber(rollNumber, request));
    }


    /* ===== GET BY ID ===== */
    @GetMapping("/get-by-id")
    public ResponseEntity<WeeklyReportResponse> getById(@RequestParam Long id) {
        return ResponseEntity.ok(weeklyReportService.getById(id));
    }

    /* ===== UPDATE ===== */
    @PatchMapping("/patch-by-id")
    public ResponseEntity<WeeklyReportResponse> update(
            @RequestParam Long id,
            @Valid @RequestBody UpdateWeeklyReportRequest request
    ) {
        return ResponseEntity.ok(weeklyReportService.update(id, request));
    }

    /* ===== GET BY BATCH + YEAR ===== */
    @GetMapping("/batch-year")
    public ResponseEntity<List<WeeklyReportResponse>> getByBatchAndYear(
            @RequestParam String batch,
            @RequestParam Integer year
    ) {
        return ResponseEntity.ok(
                weeklyReportService.getByBatchAndYear(batch, year)
        );
    }

    /* ===== ADMIN FILTER ===== */
    @GetMapping("/admin")
    public ResponseEntity<List<WeeklyReportResponse>> getForAdmin(
            @RequestParam String groupName,
            @RequestParam String batch,
            @RequestParam Integer year
    ) {
        return ResponseEntity.ok(
                weeklyReportService.getForAdmin(groupName, batch, year)
        );
    }

    @GetMapping("/weekly-reports-rollNumber")
    public List<WeeklyReportResponse> getMyWeeklyReports(
            @RequestParam(required = false) String rollNumber
    ) {
        if (rollNumber == null) {
            throw new RuntimeException("rollNumber is required");
        }
        return weeklyReportService.getWeeklyReportsForStudent(rollNumber);
    }

}
