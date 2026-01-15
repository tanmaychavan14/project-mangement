package com.example.collegeprojectm.dtoo;
import com.example.collegeprojectm.enums.MemberAction;
import lombok.Getter;

import lombok.Setter;


@Getter
@Setter
public class GroupMemberUpdateRequest {

    private String studentRollNumber;

    // action to perform
    private MemberAction action;

    // used only when action = UPDATE
    private Boolean leader;
}
