package ru.skillbox.dto.kafka;

import lombok.Data;

import java.util.UUID;

@Data
public class KafkaNewAccountEvent {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;

}
