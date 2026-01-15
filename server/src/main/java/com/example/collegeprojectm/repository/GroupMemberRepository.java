package com.example.collegeprojectm.repository;

import com.example.collegeprojectm.model.GroupMember;
import com.example.collegeprojectm.model.StudentGroup;
import com.example.collegeprojectm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroup(StudentGroup group);
    boolean existsByGroupAndStudentId( StudentGroup group, User student);
    Optional<GroupMember> findByGroupAndStudentId(StudentGroup group, User student);

    Long countByGroupAndLeaderTrue( StudentGroup group);
}
