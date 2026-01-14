package com.example.collegeprojectm.repository;

import com.example.collegeprojectm.model.GroupMember;
import com.example.collegeprojectm.model.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroup(StudentGroup group);


}
