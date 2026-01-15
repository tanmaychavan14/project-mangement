package com.example.collegeprojectm.dtoo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentGroupResponse {
    private Long groupId;
    private String groupName;
    private String batch;
    private Integer currentYear;
    private String status;

    private String projectTitle;
    private String projectDomain;

    private FacultyResponse guide;        // faculty details
    private List<GroupMemberResponse> members;
}
