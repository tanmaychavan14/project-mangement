package com.example.collegeprojectm.controller;


import com.example.collegeprojectm.dtoo.PasswordUpdateRequest;
import com.example.collegeprojectm.model.ApiResponse;
import com.example.collegeprojectm.model.User;
import com.example.collegeprojectm.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

     @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity
                    .status(HttpStatus.CREATED)   // 201
                    .body(
                            new ApiResponse<>(
                                    "User created successfully",
                                    createdUser
                            )
                    );

        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409
                    .body(ex.getMessage());
        }
        catch (Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong. Please try again.");
    }

    }



    //find user by roll number;

    @GetMapping("/getUserByRollNumber")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> getUserByRollNumber(@RequestParam String rollNumber){
        try{

            // 🔍 DEBUG — ADD THIS HERE
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("AUTH = " + auth);
            System.out.println("AUTHORITIES = " + auth.getAuthorities());
            User user = userService.getUserByRollNumber(rollNumber);
            return ResponseEntity.ok(
            new ApiResponse<>("User with roll Number " +  rollNumber + "fetch Successfully", user)

            );
        }
        catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());

        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/by-batch")
    public ResponseEntity<?> getUsersByBatch(@RequestParam String batch) {
        try {
            List<User> userList = userService.getAllUsersByBatch(batch);
            return ResponseEntity.ok(
                    new ApiResponse<>("Users List", userList)
            );
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }


    @PatchMapping("/update-user")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> updateUserData(
            @RequestParam String rollNumber,
            @RequestBody User user
    ){
          try{
              User updatedUser = userService.updateUserData(rollNumber, user);

              return ResponseEntity.ok(
                      new ApiResponse<>("User updated successfully", updatedUser)
              );
          }
          catch (RuntimeException ex){
              return ResponseEntity.status(HttpStatus.NOT_FOUND)
                      .body(ex.getMessage());

        }
          catch (Exception e){
              return ResponseEntity
                      .status(HttpStatus.INTERNAL_SERVER_ERROR)
                      .body(e.getMessage());
          }
    }
    @PatchMapping("/student/update-password")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> updatePassword(
            @RequestBody PasswordUpdateRequest request,
            Authentication authentication
    ) {
        // rollNumber comes from JWT subject
        String rollNumber = authentication.getName();

        userService.updatePassword(rollNumber, request);

        return ResponseEntity.ok("Password updated successfully");
    }


}
