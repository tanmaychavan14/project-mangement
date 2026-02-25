package com.example.collegeprojectm.event;


import lombok.Data;

@Data
public class GroupCreatedEvent {

    private Long groupId;
    private String groupName;
    private String batch;
    private Integer currentYear;
    private String guideRollNumber;

    public GroupCreatedEvent() {}

    public GroupCreatedEvent(Long groupId,
                             String groupName,
                             String batch,
                             Integer currentYear,
                             String guideRollNumber) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.batch = batch;
        this.currentYear = currentYear;
        this.guideRollNumber = guideRollNumber;
    }

    // Getters & Setters
}