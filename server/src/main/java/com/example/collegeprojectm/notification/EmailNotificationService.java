package com.example.collegeprojectm.notification;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendGroupCreatedEmail(
            String toEmail,
            String groupName,
            String batch
    ) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("New Student Group Created");
        message.setText(
                "A new group has been created.\n\n" +
                        "Group Name: " + groupName + "\n" +
                        "Batch: " + batch
        );

        mailSender.send(message);
    }
}