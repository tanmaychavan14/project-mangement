package com.example.collegeprojectm.controller;




import com.example.collegeprojectm.dtoo.WorkPlanDto;
import com.example.collegeprojectm.service.WorkPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/work-plans")
@RequiredArgsConstructor
public class WorkPlanController {

    private final WorkPlanService service;

    /* CREATE (Admin only ideally) */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody WorkPlanDto dto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.createWorkPlan(dto));
        }
        catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(ex.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
    /* UPDATE (Static data but editable) */
    @PutMapping("/update")
    public ResponseEntity<?> update(
            @RequestParam Long id,
            @RequestBody WorkPlanDto dto
    ) {
        try {
            return ResponseEntity.ok(service.updateWorkPlan(id, dto));
        }
        catch (RuntimeException ex){
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    /* GET YEAR + BATCH */
    @GetMapping("/get-work-plan")
    public ResponseEntity<?> getByYearAndBatch(
            @RequestParam Integer studentYear,
            @RequestParam String batch
    ) {
        try {
            return ResponseEntity.ok(
                    service.getWorkPlanByYearAndBatch(studentYear, batch)
            );
        }
        catch (RuntimeException ex){
          return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
        catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    /* GET BY DATE */
    @GetMapping("/date")
    public ResponseEntity<?> getByDate(
            @RequestParam Integer studentYear,
            @RequestParam LocalDate date
    ) {
        try {
            return ResponseEntity.ok(
                    service.getWorkPlanByYearAndDate(studentYear, date)
            );
        }
        catch(RuntimeException ex){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
