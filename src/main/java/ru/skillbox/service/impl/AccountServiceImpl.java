package ru.skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import ru.skillbox.annotation.LogAspect;
import ru.skillbox.aop.LogType;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.dto.AccountSearchDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;
import ru.skillbox.exception.AccountNotFoundException;
import ru.skillbox.exception.AlreadyExistsException;
import ru.skillbox.mapper.AccountUpdateFactory;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.repository.specification.AccountSpecification;
import ru.skillbox.service.AccountService;
import ru.skillbox.mapper.AccountMapper;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    @Value("${app.scheduled.offline-status-minutes}")
    private int offlineStatusMinutes;

    private final UUID testUUID = UUID.fromString("5bd73891-a00e-44e9-954c-f6610d4d1a16");

//    @Override
//    @LogAspect(type = LogType.SERVICE)
//    public AccountDto getAccount() { // TODO: Security
//        return AccountMapper.accountToAccountDto(getAccountById(testUUID));
//    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public AccountDto getAccount() { // TODO: Security
        Account account = getAccountById(testUUID);
        account.setIsOnline(true); // TODO: test. вынести в кафку
        account.setLastOnlineTime(LocalDateTime.now()); // TODO: test. вынести в кафку
        accountRepository.save(account);
        return AccountMapper.accountToAccountDto(account);
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public AccountDto getAccountDtoById(UUID accountId) {
        return AccountMapper.accountToAccountDto(getAccountById(accountId));
    }

    public Account getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        MessageFormat.format("Account not found for ID: {0}", accountId)));
    }

    @Override
    @Transactional
    @LogAspect(type = LogType.SERVICE)
    public Account createAccount(KafkaAuthEvent kafkaAuthEvent) {
        if (accountRepository.existsByEmail(kafkaAuthEvent.getEmail())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("An account with email {0} already exists!", kafkaAuthEvent.getEmail()));
        }
        return accountRepository.save(AccountMapper.kafkaAuthEventToAccount(kafkaAuthEvent));
    }

    @Override
    @Transactional
    @LogAspect(type = LogType.SERVICE)
    public AccountDto updateAccount(AccountDto accountDto) { // TODO: Security
        Account updatedAccount = getAccountById(testUUID);
        AccountUpdateFactory.updateFields(updatedAccount, accountDto);
        return AccountMapper.accountToAccountDto(accountRepository.save(updatedAccount));
    }

    @Override
    @Transactional
    @LogAspect(type = LogType.SERVICE)
    public void deleteAccount() { // TODO: Security
        accountRepository.updateDeleted(testUUID, true);
    }

    @Override
    @Transactional
    @LogAspect(type = LogType.SERVICE)
    public void manageAccountBlock(UUID accountId, boolean block) {  // TODO: Security (только админ)
        int updated = accountRepository.updateBlocked(accountId, block);
        if (updated == 0) {
            throw new AccountNotFoundException(MessageFormat.format("Account not found for ID: {0}", accountId));
        }
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public List<UUID> getAllAccountIds() {
        return accountRepository.findAllIds();
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public PageImpl<AccountDto> getAccountsByTheirId(List<UUID> ids, Pageable page) {
        Page<Account> accountsPage = accountRepository.findAccountsByIds(ids, page);
        List<AccountDto> accountDtoList = AccountMapper.accountListToAccountDtoList(accountsPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public PageImpl<AccountDto> getAllAccounts(Pageable page) {
        Page<Account> accountsPage = accountRepository.findAll(page);
        List<AccountDto> accountDtoList = AccountMapper.accountListToAccountDtoList(accountsPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public PageImpl<AccountDto> filterBy(AccountSearchDto accountSearchDto, Pageable page) {
        Page<Account> accountsPage = accountRepository.findAll(AccountSpecification.withFilter(accountSearchDto), page);
        List<AccountDto> accountDtoList = AccountMapper.accountListToAccountDtoList(accountsPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public PageImpl<AccountDto> searchAccount(AccountSearchDto accountSearchDto, Pageable page) {
        return filterBy(accountSearchDto, page);
    }

    @Transactional
    @Scheduled(cron = "${app.scheduled.interval-in-cron}")
    public void updateOfflineStatus() {
        try {
            accountRepository.updateOfflineStatus(LocalDateTime.now().minusMinutes(offlineStatusMinutes));
        } catch (InvalidDataAccessApiUsageException e) {
            log.error("Error updating offline status: {}", e.getMessage(), e);
        }
    }
}
