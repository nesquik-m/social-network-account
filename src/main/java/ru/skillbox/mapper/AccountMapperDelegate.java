package ru.skillbox.mapper;

import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AccountMapperDelegate implements AccountMapper {

    @Override
    public Account kafkaAuthEventToAccount(KafkaAuthEvent kafkaAuthEvent) {
        return Account.builder()
                .id(UUID.randomUUID())
                .email(kafkaAuthEvent.getEmail())
                .firstName(kafkaAuthEvent.getFirstName())
                .lastName(kafkaAuthEvent.getLastName())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }
}
