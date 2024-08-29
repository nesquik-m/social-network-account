package ru.skillbox.dto.kafka;

import lombok.Data;

@Data
public class KafkaAuthEvent {

    private String email;
    private String firstName;
    private String lastName;

}
