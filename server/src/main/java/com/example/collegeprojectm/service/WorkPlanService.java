package com.example.collegeprojectm.service;


import com.example.collegeprojectm.dtoo.WorkPlanDto;
import com.example.collegeprojectm.model.WorkPlan;
import com.example.collegeprojectm.repository.WorkPlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkPlanService {

    private final WorkPlanRepository repository;

    public WorkPlanService(WorkPlanRepository repository) {

    this.repository =repository;
}



    public WorkPlanDto createWorkPlan(WorkPlanDto dto) {

        boolean exists = repository.existsByStudentYearAndBatchAndWorkDate(
                dto.getStudentYear(),
                dto.getBatch(),
                dto.getWorkDate()
        );

        if (exists) {
            throw new RuntimeException(
                    "Work plan already exists for this year, batch and date"
            );
        }

        WorkPlan plan = mapToEntity(dto);
        return mapToDto(repository.save(plan));
    }



    public WorkPlanDto updateWorkPlan(Long id, WorkPlanDto dto) {
        WorkPlan plan = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkPlan not found"));

        plan.setStudentYear(dto.getStudentYear());
        plan.setBatch(dto.getBatch());
        plan.setWorkDate(dto.getWorkDate());
        plan.setExpectedWork(dto.getExpectedWork());

        return mapToDto(repository.save(plan));
    }


    public List<WorkPlanDto> getWorkPlanByYearAndBatch(Integer studentYear, String batch) {

        List<WorkPlan> plans =
                repository.findByStudentYearAndBatch(studentYear, batch);

        if (plans.isEmpty()) {
            throw new RuntimeException(
                    "No work plan found for year " + studentYear +
                            " and batch " + batch
            );
        }

        return plans.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public WorkPlanDto getWorkPlanByYearAndDate(Integer studentYear, LocalDate date) {
        WorkPlan plan = repository.findByStudentYearAndWorkDate(studentYear, date)
                .orElseThrow(() -> new RuntimeException("WorkPlan not found"));
        return mapToDto(plan);
    }



    private WorkPlanDto mapToDto(WorkPlan plan) {
        WorkPlanDto dto = new WorkPlanDto();
        dto.setId(plan.getId());
        dto.setStudentYear(plan.getStudentYear());
        dto.setBatch(plan.getBatch());
        dto.setWorkDate(plan.getWorkDate());
        dto.setExpectedWork(plan.getExpectedWork());
        return dto;
    }

    private WorkPlan mapToEntity(WorkPlanDto dto) {
        WorkPlan plan = new WorkPlan();
        plan.setStudentYear(dto.getStudentYear());
        plan.setBatch(dto.getBatch());
        plan.setWorkDate(dto.getWorkDate());
        plan.setExpectedWork(dto.getExpectedWork());
        return plan;
    }
}
