package com.example.collegeprojectm.controller;


import com.example.collegeprojectm.dtoo.CreateStudentGroupRequest;
import com.example.collegeprojectm.dtoo.StudentGroupResponse;
import com.example.collegeprojectm.dtoo.UpdateStudentGroupRequest;
import com.example.collegeprojectm.service.user.StudentGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/groups")
public class StudentGroupController {

    private final StudentGroupService service;
    public StudentGroupController(StudentGroupService service) {
        this.service = service;
    }

    @PostMapping("/create-group")
    public ResponseEntity<String> createGroup(
            @RequestBody CreateStudentGroupRequest request) {
        try{
            service.createGroupWithMembers(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Student group created successfully");
        }
        catch(RuntimeException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }

    @GetMapping("/get-group-data-nby")
    public ResponseEntity<?> getGroup(
            @RequestParam String groupName,
            @RequestParam String batch,
            @RequestParam Integer currentYear) {

        try {


            return ResponseEntity.ok(
                    service.getGroupDetails(groupName, batch, currentYear)
            );
        }
        catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
        catch(Exception  e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }


    @GetMapping("/student/group")
    public ResponseEntity<?> getMyGroup(
            @RequestParam String rollNumber
    ) {
        return ResponseEntity.ok(
                service.getGroupDetailsByRollNumber(rollNumber)
        );
    }

    @GetMapping("/groups-batch-year")
    public ResponseEntity<?> getGroupsByBatchAndYear(
            @RequestParam String batch,
            @RequestParam Integer year
    ) {
        return ResponseEntity.ok(
                service.getGroupsByBatchAndYear(batch, year)
        );
    }
    @PatchMapping("/update-group")
    public ResponseEntity<String> updateGroup(


            @RequestParam Long groupId,
            @RequestBody UpdateStudentGroupRequest request) {
        try {
            service.updateGroupAndMembers(groupId, request);
            return ResponseEntity.ok("Group updated successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/update-group-rollNumber")
    public ResponseEntity<String> updateGroupByRollNumber(
            @RequestParam String rollNumber,
            @RequestBody UpdateStudentGroupRequest request
    ) {
        try {
            service.updateGroupAndMembersByRollNumber(rollNumber, request);
            return ResponseEntity.ok("Group updated successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }





}
