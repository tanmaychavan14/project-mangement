package com.example.collegeprojectm.repository;

import com.example.collegeprojectm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>findByRollNumberAndBatch(String rollNumber, String batch);
    Optional<User> findByRollNumber(String rollNumber);
    List<User> findByBatch(String batch);
    boolean existsByRollNumberAndBatch(String rollNumber, String batch);
}