package ru.skillbox.mapper;

import lombok.experimental.UtilityClass;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.dto.kafka.KafkaNewAccountEvent;
import ru.skillbox.entity.Account;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class AccountMapper {

    public Account kafkaAuthEventToAccount(KafkaAuthEvent kafkaAuthEvent) {
        return Account.builder()
                .id(UUID.randomUUID())
                .email(kafkaAuthEvent.getEmail())
                .firstName(kafkaAuthEvent.getFirstName().trim())
                .lastName(kafkaAuthEvent.getLastName().trim())
                .isBlocked(false)
                .isDeleted(false)
                .isOnline(true)
                .build();
    }

    public Account accountDtoToAccount(AccountDto accountDto) {
        return Account.builder()
                .id(accountDto.getId())
                .email(accountDto.getEmail())
                .city(accountDto.getCity())
                .country(accountDto.getCountry())
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .birthDate(accountDto.getBirthDate())
                .isBlocked(accountDto.getIsBlocked())
                .isDeleted(accountDto.getIsDeleted())
                .isOnline(accountDto.getIsOnline())
                .createdOn(accountDto.getCreatedOn())
                .updatedOn(accountDto.getUpdatedOn())
                .phone(accountDto.getPhone())
                .about(accountDto.getAbout())
                .profileCover(accountDto.getProfileCover())
                .emojiStatus(accountDto.getEmojiStatus())
                .photo(accountDto.getPhoto())
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
                .photo(account.getPhoto())
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

    public List<AccountDto> accountListToAccountDtoList(List<Account> accounts) {
        return accounts.stream()
                .map(AccountMapper::accountToAccountDto)
                .collect(Collectors.toList());
    }

}
