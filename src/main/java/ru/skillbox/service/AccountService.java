package ru.skillbox.service;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.dto.AccountSearchDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDto getAccount();

    Account getAccountById(UUID accountId);

    AccountDto getAccountDtoById(UUID accountId);

    Account createAccount(KafkaAuthEvent kafkaAuthEvent);

    AccountDto updateAccount(AccountDto accountDto);

    void deleteAccount();

    void manageAccountBlock(UUID accountId, boolean block);

    List<UUID> getAllAccountIds();

    PageImpl<AccountDto> getAccountsByTheirId(List<UUID> ids, Pageable page);

    PageImpl<AccountDto> getAllAccounts(Pageable page);

    PageImpl<AccountDto> filterBy(AccountSearchDto accountSearchDto, Pageable page);

    PageImpl<AccountDto> searchAccount (AccountSearchDto accountSearchDto, Pageable page);

//    Account getAccountById(UUID accountId);
//
//    Account createAccount(KafkaAuthEvent kafkaAuthEvent);
//
//    Account updateAccount(UUID accountId, Account account);
//
//    void deleteAccount(UUID accountId);
//
//    void manageAccountBlock(UUID accountId, UUID blockedAccountId, boolean block);
//
//    List<UUID> getAllAccountIds();
//
//    List<Account> getAccountsByTheirId(List<UUID> ids, Pageable pageable);
//
//    List<Account> getAllAccounts(Pageable pageable);
//
//    Page<Account> filterBy(AccountSearchDto accountSearchDto, Pageable pageable);
//
//    List<Account> searchAccount (AccountSearchDto accountSearchDto);

}
