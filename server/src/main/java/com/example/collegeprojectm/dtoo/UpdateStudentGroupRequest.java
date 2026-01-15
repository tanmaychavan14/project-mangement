package com.example.collegeprojectm.dtoo;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateStudentGroupRequest {

    // group fields (optional)
    private String groupName;
    private String projectTitle;
    private String projectDomain;
    private String status;
    private String guideRollNumber;

    // members update
    private List<GroupMemberUpdateRequest> members;
}

