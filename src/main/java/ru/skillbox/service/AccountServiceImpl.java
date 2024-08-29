package ru.skillbox.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.dto.AccountSearchDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;
import ru.skillbox.exception.AccountNotFoundException;
import ru.skillbox.exception.AlreadyExistsException;
import ru.skillbox.exception.BadRequestException;
import ru.skillbox.mapper.AccountMapper;
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

    private final AccountMapper accountMapper;

    @Override
    public Account getAccountById(UUID accountId) {
        log.info("Get account by ID: {}", accountId);
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        MessageFormat.format("Account not found for ID: {0}", accountId)));
    }

    @Override
    public Account createAccount(KafkaAuthEvent kafkaAuthEvent) {
        if (accountRepository.existsByEmail(kafkaAuthEvent.getEmail())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("Аккаунт с email {0} уже существует!", kafkaAuthEvent.getEmail()));
        }
        log.info("Create account by email: {}", kafkaAuthEvent.getEmail());
        return accountRepository.save(accountMapper.kafkaAuthEventToAccount(kafkaAuthEvent));
    }

    @Override
    @Transactional
    public Account updateAccount(UUID accountId, Account account) {
        log.info("Update account with ID: {}", accountId);
        Account updatedAccount = getAccountById(accountId);
        BeanUtils.copyNonNullProperties(account, updatedAccount);
        updatedAccount.setUpdatedOn(LocalDateTime.now());
        return accountRepository.save(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(UUID accountId) {
        log.info("Delete account with ID: {}", accountId);
        Account updatedAccount = getAccountById(accountId);
        updatedAccount.setIsDeleted(true);
        accountRepository.save(updatedAccount);
    }

    @Override
    @Transactional
    public void updateBlocked(UUID accountId, boolean isBlocked) {
        int updated = accountRepository.updateBlocked(accountId, isBlocked);
        if (updated == 0) {

            if (accountRepository.findById(accountId).isEmpty()) {
                throw new AccountNotFoundException(MessageFormat.format("Account not found for ID: {0}", accountId));
            }

            throw new BadRequestException(
                    MessageFormat.format("Blocked status for account with ID {0} was not updated", accountId));
        }
        log.info("Update blocked status '{}' for account with ID: {}", isBlocked, accountId);
    }

    @Override
    public List<UUID> getAllAccountIds() {
        log.info("Get all account IDs");
        return accountRepository.findAllIds();
    }

    @Override
    public List<Account> getAccountsByTheirId(List<UUID> ids, Pageable pageable) {
        log.info("Get accounts by IDs");
        return accountRepository.findAccountsByIds(ids, pageable).getContent();
    }

    @Override
    public List<Account> getAllAccounts(Pageable pageable) {
        log.info("Get all accounts");
        return accountRepository.findAll(pageable).getContent();
    }

    @Override
    public Page<Account> filterBy(AccountSearchDto accountSearchDto, Pageable pageable) {
        log.info("Get accounts by filter");
        return accountRepository.findAll(AccountSpecification.withFilter(accountSearchDto), pageable);
    }

    @Override
    public List<Account> searchAccount(AccountSearchDto accountSearchDto) {
        log.info("Search account");
        return List.of();
    }

}
