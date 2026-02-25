package com.example.collegeprojectm.kafka;

import com.example.collegeprojectm.event.GroupCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GroupEventProducer {

    private final KafkaTemplate<String, GroupCreatedEvent> kafkaTemplate;

    public GroupEventProducer(KafkaTemplate<String, GroupCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishGroupCreatedEvent(GroupCreatedEvent event) {
        kafkaTemplate.send("group-created-topic", event);
    }
}