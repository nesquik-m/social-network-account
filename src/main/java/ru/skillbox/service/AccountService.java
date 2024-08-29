package ru.skillbox.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.skillbox.dto.AccountSearchDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {



    Account getAccountById(UUID accountId);

    Account createAccount(KafkaAuthEvent kafkaAuthEvent);

    Account updateAccount(UUID accountId, Account account);

    void deleteAccount(UUID accountId);

    void blockAccount(UUID accountId);

    void unblockAccount(UUID accountId);

    List<UUID> getAllAccountIds();

    List<Account> getAccountsByTheirId(List<UUID> ids, Pageable pageable);

    List<Account> getAllAccounts(Pageable pageable);

    // ----- до исправлений
//    List<Account> filterBy(AccountSearchDto accountSearchDto, Pageable pageable);
    // -----

    Page<Account> filterBy(AccountSearchDto accountSearchDto, Pageable pageable);

    List<Account> searchAccount (AccountSearchDto accountSearchDto);

}
