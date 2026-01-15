package com.example.collegeprojectm.service.user;
import com.example.collegeprojectm.dtoo.*;
import com.example.collegeprojectm.model.GroupMember;
import com.example.collegeprojectm.model.StudentGroup;
import com.example.collegeprojectm.model.User;
import com.example.collegeprojectm.repository.GroupMemberRepository;
import com.example.collegeprojectm.repository.StudentGroupRepository;
import com.example.collegeprojectm.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentGroupService {

   private final StudentGroupRepository studentGroupRepository;
   private final GroupMemberRepository memberRepository;
   private final UserRepository userRepository;


    public StudentGroupService(StudentGroupRepository studentGroupRepository ,
                               GroupMemberRepository groupMemberRepository,
                               UserRepository userRepository){

        this.studentGroupRepository = studentGroupRepository;
        this.memberRepository = groupMemberRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public void createGroupWithMembers(CreateStudentGroupRequest request) {

        // 1️⃣ Check uniqueness
        if (studentGroupRepository.existsByGroupNameAndBatchAndCurrentYear(
                request.getGroupName(),
                request.getBatch(),
                request.getCurrentYear())) {
            throw new RuntimeException("Group already exists");
        }

        // 2️⃣ Create StudentGroup
        StudentGroup group = new StudentGroup();
        group.setGroupName(request.getGroupName());
        group.setBatch(request.getBatch());
        group.setProjectTitle(request.getProjectTitle());
        group.setProjectDomain(request.getProjectDomain());
        group.setCurrentYear(request.getCurrentYear());
        group.setStatus(request.getStatus());

        if (request.getGuideRollNumber() != null) {
            User guide = userRepository.findByRollNumber(
                    request.getGuideRollNumber()
            ).orElseThrow(() -> new RuntimeException("Guide not found"));

            group.setGuideId(guide);
        }


        StudentGroup savedGroup = studentGroupRepository.save(group);

        // 3️⃣ Create Group Members
        for (GroupMemberRequest memberReq : request.getMembers()) {

            GroupMember member = new GroupMember();
            member.setGroup(savedGroup);
            User student = userRepository
                    .findByRollNumber(memberReq.getStudentRollNumber())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            member.setStudentId(student);

            member.setCurrentYear(request.getCurrentYear());
            member.setLeader(memberReq.isLeader());

            memberRepository.save(member);
        }


    }


    public StudentGroupResponse getGroupDetails(
            String groupName, String batch, Integer currentYear) {

        StudentGroup group = studentGroupRepository
                .findByGroupNameAndBatchAndCurrentYear(groupName, batch, currentYear)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        StudentGroupResponse response = new StudentGroupResponse();
        response.setGroupId(group.getId());
        response.setGroupName(group.getGroupName());
        response.setBatch(group.getBatch());
        response.setCurrentYear(group.getCurrentYear());
        response.setStatus(group.getStatus());
        response.setProjectTitle(group.getProjectTitle());
        response.setProjectDomain(group.getProjectDomain());

        // faculty
        if (group.getGuideId() != null) {

                FacultyResponse faculty = new FacultyResponse();
                faculty.setName(group.getGuideId().getName());
                faculty.setRollNumber(group.getGuideId().getRollNumber());
                response.setGuide(faculty);


        }

        // members
        List<GroupMember> members = memberRepository.findByGroup(group);

        List<GroupMemberResponse> memberResponses = members.stream().map(m -> {
            GroupMemberResponse r = new GroupMemberResponse();
            r.setStudentRollNumber(m.getStudentId().getRollNumber());
            r.setStudentName(m.getStudentId().getName());
            r.setLeader(m.isLeader());
            return r;
        }).toList();

        response.setMembers(memberResponses);

        return response;
    }



    @Transactional
    public void updateGroupAndMembers(
            Long groupId,
            UpdateStudentGroupRequest request) {

        StudentGroup group = studentGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // -------- update group fields --------

        if(request.getGroupName()!= null){
            group.setGroupName(request.getGroupName());
        }
        if (request.getProjectTitle() != null)
            group.setProjectTitle(request.getProjectTitle());

        if (request.getProjectDomain() != null)
            group.setProjectDomain(request.getProjectDomain());

        if (request.getStatus() != null)
            group.setStatus(request.getStatus());

        if (request.getGuideRollNumber() != null) {
            User guide = userRepository.findByRollNumber(
                    request.getGuideRollNumber()
            ).orElseThrow(() -> new RuntimeException("Guide not found"));

            group.setGuideId(guide);
        }

        // -------- update members --------
        if (request.getMembers() == null) return;

        for (GroupMemberUpdateRequest m : request.getMembers()) {

            User student = userRepository.findByRollNumber(
                    m.getStudentRollNumber()
            ).orElseThrow(() -> new RuntimeException("Student not found"));

            switch (m.getAction()) {

                case ADD -> {
                    if (memberRepository
                            .existsByGroupAndStudentId(group, student)) {
                        throw new RuntimeException("Student already in group");
                    }

                    GroupMember member = new GroupMember();
                    member.setGroup(group);
                    member.setStudentId(student);
                    member.setCurrentYear(group.getCurrentYear());
                    member.setLeader(Boolean.TRUE.equals(m.getLeader()));

                    memberRepository.save(member);
                }

                case UPDATE -> {
                    GroupMember member = memberRepository
                            .findByGroupAndStudentId(group, student)
                            .orElseThrow(() ->
                                    new RuntimeException("Member not found"));

                    if (m.getLeader() != null)
                        member.setLeader(m.getLeader());
                }

                case REMOVE -> {
                    GroupMember member = memberRepository
                            .findByGroupAndStudentId(group, student)
                            .orElseThrow(() ->
                                    new RuntimeException("Member not found"));

                    memberRepository.delete(member);
                }
            }
        }

        // optional: ensure only one leader
        validateSingleLeader(group);
    }
    private void validateSingleLeader(StudentGroup group) {
        long leaders = memberRepository
                .countByGroupAndLeaderTrue(group);

        if (leaders != 1) {
            throw new RuntimeException("Exactly one leader required");
        }
    }


}
