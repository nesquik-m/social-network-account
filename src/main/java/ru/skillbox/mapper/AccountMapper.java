package ru.skillbox.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.entity.Account;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    Account accountDtoToAccount(AccountDto accountDto);

    AccountDto accountToAccountDto(Account accountDto);

    List<AccountDto> accountListToAccountDtoList(List<Account> accounts);

}
