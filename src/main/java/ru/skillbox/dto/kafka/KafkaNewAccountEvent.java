package ru.skillbox.dto.kafka;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class KafkaNewAccountEvent {

//    private UUID code;
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;

}
