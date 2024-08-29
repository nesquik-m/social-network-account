package ru.skillbox.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.dto.kafka.KafkaNewAccountEvent;
import ru.skillbox.entity.Account;

import java.util.List;

@DecoratedWith(AccountMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    Account accountDtoToAccount(AccountDto accountDto);

    AccountDto accountToAccountDto(Account accountDto);

    List<AccountDto> accountListToAccountDtoList(List<Account> accounts);

    Account kafkaAuthEventToAccount(KafkaAuthEvent kafkaAuthEvent);

    KafkaNewAccountEvent accountToKafkaNewAccountEvent(Account account);

}
