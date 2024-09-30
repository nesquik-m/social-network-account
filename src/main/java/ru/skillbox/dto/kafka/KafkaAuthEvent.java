package ru.skillbox.dto.kafka;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class KafkaAuthEvent {

    private UUID uuid;
    private String email;
    private String firstName;
    private String lastName;

}
