package com.example.collegeprojectm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthChecker {

    @GetMapping("/health-checker")
    public String health_checker(){
    return " server is running on port 8080";}
}
