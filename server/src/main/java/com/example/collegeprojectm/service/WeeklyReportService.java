package com.example.collegeprojectm.service;

import com.example.collegeprojectm.controller.WeeklyReportController;
import com.example.collegeprojectm.dtoo.CreateWeeklyReportRequest;
import com.example.collegeprojectm.dtoo.UpdateWeeklyReportRequest;
import com.example.collegeprojectm.dtoo.WeeklyReportResponse;
import com.example.collegeprojectm.model.*;
import com.example.collegeprojectm.repository.*;
import com.example.collegeprojectm.service.user.StudentGroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class WeeklyReportService {

    private final WeeklyReportRepository weeklyReportRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final WorkPlanRepository workPlanRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;

    public WeeklyReportService(WeeklyReportRepository weeklyReportRepository, StudentGroupRepository studentGroupRepository
    , WorkPlanRepository workPlanRepository, UserRepository userRepository, GroupMemberRepository groupMemberRepository){
        this.studentGroupRepository=studentGroupRepository;
        this.weeklyReportRepository = weeklyReportRepository;
        this.workPlanRepository=workPlanRepository;
        this.userRepository =userRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    /* ================= CREATE ================= */
    public WeeklyReportResponse create(CreateWeeklyReportRequest request) {

        weeklyReportRepository
                .findByGroupId_IdAndStudentYearAndWorkDate(
                        request.getGroupId(),
                        request.getStudentYear(),
                        request.getWorkDate()
                )
                .ifPresent(r -> {
                    throw new RuntimeException("Weekly report already exists");
                });

        StudentGroup group = studentGroupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        WorkPlan workPlan = workPlanRepository.findById(request.getWorkPlanId())
                .orElseThrow(() -> new RuntimeException("WorkPlan not found"));

        WeeklyReport report = new WeeklyReport();
        report.setGroupId(group);
        report.setStudentYear(request.getStudentYear());
        report.setWorkDate(request.getWorkDate());
        report.setWorkPlanId(workPlan);
        report.setWorkDone(request.getWorkDone());
        report.setGuideFeedback(request.getGuideFeedback());
        report.setProgressPercentage(request.getProgressPercentage());

        return mapToResponse(weeklyReportRepository.save(report));
    }

    /* ================= GET BY ID ================= */
    public WeeklyReportResponse getById(Long id) {
        return mapToResponse(
                weeklyReportRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Weekly report not found"))
        );
    }

    /* ================= UPDATE ================= */
    public WeeklyReportResponse update(Long id, UpdateWeeklyReportRequest request) {

        WeeklyReport report = weeklyReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weekly report not found"));

        if (request.getWorkDone() != null)
            report.setWorkDone(request.getWorkDone());

        if (request.getGuideFeedback() != null)
            report.setGuideFeedback(request.getGuideFeedback());

        if (request.getProgressPercentage() != null)
            report.setProgressPercentage(request.getProgressPercentage());

        return mapToResponse(weeklyReportRepository.save(report));
    }

    public List<WeeklyReportResponse> getWeeklyReportsForStudent(String rollNumber) {

        // 1️⃣ Get user
        User user = userRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Get group membership for current year
        GroupMember member = groupMemberRepository
                .findByStudentId_IdAndCurrentYear(
                        user.getId(),
                        user.getCurrentYear()
                )
                .orElseThrow(() -> new RuntimeException("Group not assigned"));

        Long groupId = member.getGroup().getId();

        // 3️⃣ Fetch weekly reports directly
        return weeklyReportRepository
                .findByGroupId_IdAndStudentYear(groupId, user.getCurrentYear())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public WeeklyReportResponse createByRollNumber(
            String rollNumber,
            CreateWeeklyReportRequest request
    ) {

        // 1️⃣ Find user
        User user = userRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Find group membership
        GroupMember member = groupMemberRepository
                .findByStudentId_IdAndCurrentYear(
                        user.getId(),
                        user.getCurrentYear()
                )
                .orElseThrow(() -> new RuntimeException("User not in any group"));

        StudentGroup group = member.getGroup();

        // 3️⃣ Check duplicate
        weeklyReportRepository
                .findByGroupId_IdAndStudentYearAndWorkDate(
                        group.getId(),
                        user.getCurrentYear(),
                        request.getWorkDate()
                )
                .ifPresent(r -> {
                    throw new RuntimeException("Weekly report already exists");
                });

        // 4️⃣ Validate work plan
        WorkPlan workPlan = workPlanRepository.findById(request.getWorkPlanId())
                .orElseThrow(() -> new RuntimeException("WorkPlan not found"));

        WeeklyReport report = new WeeklyReport();
        report.setGroupId(group);
        report.setStudentYear(user.getCurrentYear());
        report.setWorkDate(request.getWorkDate());
        report.setWorkPlanId(workPlan);
        report.setWorkDone(request.getWorkDone());
        report.setProgressPercentage(request.getProgressPercentage());

        // ❌ student cannot set guide feedback
        report.setGuideFeedback(null);

        return mapToResponse(weeklyReportRepository.save(report));
    }


    /* ================= GET BY BATCH + YEAR ================= */
    public List<WeeklyReportResponse> getByBatchAndYear(String batch, Integer year) {
        return weeklyReportRepository
                .findByGroupId_BatchAndStudentYear(batch, year)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ================= GET BY GROUP + BATCH + YEAR ================= */
    public List<WeeklyReportResponse> getForAdmin(
            String groupName,
            String batch,
            Integer year
    ) {
        return weeklyReportRepository
                .findByGroupId_GroupNameAndGroupId_BatchAndStudentYear(groupName, batch, year)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public List<WeeklyReportResponse> getWeeklyReportsForGroup(Long groupId) {

        // optional validation
        studentGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        return weeklyReportRepository
                .findByGroupId_Id(groupId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }



    /* ================= MAPPER ================= */
    private WeeklyReportResponse mapToResponse(WeeklyReport report) {
        WeeklyReportResponse res = new WeeklyReportResponse();
        res.setId(report.getId());
        res.setGroupName(report.getGroupId().getGroupName());
        res.setBatch(report.getGroupId().getBatch());
        res.setStudentYear(report.getStudentYear());
        res.setWorkDate(report.getWorkDate());
        res.setExpectedWork(report.getWorkPlanId().getExpectedWork());
        res.setWorkDone(report.getWorkDone());
        res.setGuideFeedback(report.getGuideFeedback());
        res.setProgressPercentage(report.getProgressPercentage());
        return res;
    }
}
