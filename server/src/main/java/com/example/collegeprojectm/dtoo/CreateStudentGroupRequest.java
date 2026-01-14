package com.example.collegeprojectm.dtoo;

import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStudentGroupRequest {

    private String groupName;
    private String batch;
    private String projectTitle;
    private String projectDomain;
    private String guideRollNumber;     // user id
    private Integer currentYear;
    private String status;

    private List<GroupMemberRequest> members;
}
