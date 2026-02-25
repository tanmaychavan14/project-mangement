package com.example.collegeprojectm.service.user;
import com.example.collegeprojectm.dtoo.PasswordUpdateRequest;
import com.example.collegeprojectm.model.User;
import com.example.collegeprojectm.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class UserService {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    //Create the User;
    public User createUser(User user){
        boolean exists = userRepository.existsByRollNumberAndBatch(user.getRollNumber(),user.getBatch());

        if(exists){
            throw new RuntimeException(
                    "User already exists with roll number " +
                            user.getRollNumber() +
                            " in batch " +
                            user.getBatch()
            );}
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

//Get User with Roll Number;
    public User getUserByRollNumber(String rollNumber){

        return userRepository.findByRollNumber(rollNumber)
                .orElseThrow(()->
                        new RuntimeException(
                                "user not found with roll Number " + rollNumber
                        ));

    }

    //get all user by batch

    public List<User> getAllUsersByBatch(String batch) {

        List<User> users = userRepository.findByBatch(batch);
        if (users.isEmpty()) {
            throw new RuntimeException("No users found for batch " + batch);
        }
        return users;
    }

    public User updateUserData( String rollNumber, User user){
        User existingUser = getUserByRollNumber(rollNumber);
         if(existingUser==null){
             throw new RuntimeException(("User Not Found" + user.getRollNumber()));
         }
        if(user.getBatch() != null){
            existingUser.setBatch(user.getBatch());
        }
        if(user.getName()!=null){
            existingUser.setName(user.getName());
        }

        if(user.getStatus()!=null){
            existingUser.setStatus(user.getStatus());
        }

        if(user.getRole()!=null){
            existingUser.setRole(user.getRole());
        }

        if(user.getCurrentYear()!=null){
            existingUser.setCurrentYear(user.getCurrentYear());
        }
       return userRepository.save(existingUser);



    }

    public void updatePassword(
            String loggedInRollNumber,
            PasswordUpdateRequest request
    ) {

        User user = userRepository.findByRollNumber(loggedInRollNumber)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        // verify current password
        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword()
        )) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        // encode & update new password
        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);
    }
}
