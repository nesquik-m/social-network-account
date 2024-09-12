package ru.skillbox.dto.kafka;

import lombok.Data;

import java.util.UUID;

@Data
public class KafkaAuthEvent {

    private UUID uuid;
    private String email;
    private String firstName;
    private String lastName;

}
