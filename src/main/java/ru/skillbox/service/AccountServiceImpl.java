package ru.skillbox.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.dto.AccountByFilterDto;
import ru.skillbox.dto.AccountSearchDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;
import ru.skillbox.exception.AccountNotFoundException;
import ru.skillbox.exception.AlreadyExistsException;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.repository.AccountSpecification;
import ru.skillbox.utils.BeanUtils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        MessageFormat.format("Пользователь с ID {0} не найден!", accountId)));
    }

    @Override
    public Account createAccount(KafkaAuthEvent kafkaAuthEvent) {

        if (accountRepository.existsByEmail(kafkaAuthEvent.getEmail())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("Аккаунт с email {0} уже существует!", kafkaAuthEvent.getEmail()));
        }

        Account createdAccount = Account.builder()
                .id(UUID.randomUUID())
                .email(kafkaAuthEvent.getEmail())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        return accountRepository.save(createdAccount);
    }

    @Override
    @Transactional
    public Account updateAccount(UUID accountId, Account account) {
        Account updatedAccount = getAccountById(accountId);
        BeanUtils.copyNonNullProperties(account, updatedAccount);
        updatedAccount.setUpdatedOn(LocalDateTime.now());
        return accountRepository.save(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(UUID accountId) {
        Account updatedAccount = getAccountById(accountId);
        updatedAccount.setDeleted(true);
        accountRepository.save(updatedAccount);
    }

    // нативным запросом поменять boolean, не тащить весь объект!
    @Override
    @Transactional
    public void blockAccount(UUID accountId) {
        Account updatedAccount = getAccountById(accountId);
        updatedAccount.setBlocked(true);
        accountRepository.save(updatedAccount);
    }

    @Override
    public void unblockAccount(UUID accountId) {
        Account updatedAccount = getAccountById(accountId);
        updatedAccount.setBlocked(false);
        accountRepository.save(updatedAccount);
    }

    @Override
    public List<UUID> getAllAccountIds() {
        return accountRepository.findAllIds();
    }

    @Override
    public List<Account> getAccountsByTheirId(List<UUID> ids, Pageable pageable) {
        return accountRepository.findAccountsByIds(ids, pageable).getContent();
    }

    @Override
    public List<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).getContent();
    }

    // ----- до исправлений
//    @Override
//    public List<Account> filterBy(AccountSearchDto accountSearchDto, Pageable pageable) {
//        return accountRepository.findAll(AccountSpecification.withFilter(accountSearchDto), pageable).getContent();
//    }
    // -----

    @Override
    public Page<Account> filterBy(AccountSearchDto accountSearchDto, Pageable pageable) {
        return accountRepository.findAll(AccountSpecification.withFilter(accountSearchDto), pageable);
    }

    @Override
    public List<Account> searchAccount(AccountSearchDto accountSearchDto) {
        return List.of();
    }

}
