package com.example.collegeprojectm.kafka;

import com.example.collegeprojectm.event.GroupCreatedEvent;
import com.example.collegeprojectm.notification.EmailNotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GroupEventConsumer {

    private final EmailNotificationService emailService;

    // 👇 Put your testing email here
    private static final String TEST_EMAIL = "crazyhitman45@gmail.com";

    public GroupEventConsumer(EmailNotificationService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "group-created-topic",
            groupId = "notification-service"
    )
    public void consume(GroupCreatedEvent event) {

        System.out.println("Received GroupCreatedEvent for group: "
                + event.getGroupName());

        try {

            emailService.sendGroupCreatedEmail(
                    TEST_EMAIL,
                    event.getGroupName(),
                    event.getBatch()
            );

            System.out.println("Test email sent successfully!");

        } catch (Exception e) {
            System.err.println("Email sending failed");
            e.printStackTrace();
        }
    }
}