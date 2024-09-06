package ru.skillbox.utils;

import lombok.experimental.UtilityClass;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.dto.kafka.KafkaNewAccountEvent;
import ru.skillbox.entity.Account;

import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class AccMapper {

    public Account kafkaAuthEventToAccount(KafkaAuthEvent kafkaAuthEvent) {
        return Account.builder()
                .id(UUID.randomUUID())
                .email(kafkaAuthEvent.getEmail())
                .firstName(kafkaAuthEvent.getFirstName().trim())
                .lastName(kafkaAuthEvent.getLastName().trim())
                .isBlocked(false)
                .isDeleted(false)
                .isOnline(true)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    public AccountDto accountToAccountDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .city(account.getCity())
                .country(account.getCountry())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .birthDate(account.getBirthDate())
                .isBlocked(account.getIsBlocked())
                .isDeleted(account.getIsDeleted())
                .isOnline(account.getIsOnline())
                .createdOn(account.getCreatedOn())
                .updatedOn(account.getUpdatedOn())
                .phone(account.getPhone())
                .about(account.getAbout())
                .profileCover(account.getProfileCover())
                .emojiStatus(account.getEmojiStatus())
                .build();
    }

    public KafkaNewAccountEvent accountToKafkaNewAccountEvent(Account account) {
        return KafkaNewAccountEvent.builder()
                .id(account.getId())
                .email(account.getEmail())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .build();
    }


}
