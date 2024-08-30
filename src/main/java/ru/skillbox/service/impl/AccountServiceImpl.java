package ru.skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.dto.AccountSearchDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.BlockAccount;
import ru.skillbox.exception.AccountNotFoundException;
import ru.skillbox.exception.AlreadyExistsException;
import ru.skillbox.exception.BadRequestException;
import ru.skillbox.mapper.AccountMapper;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.repository.specification.AccountSpecification;
import ru.skillbox.repository.BlockAccountRepository;
import ru.skillbox.service.AccountService;
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

    private final BlockAccountRepository blockAccountRepository;

    private final AccountMapper accountMapper;

    private final UUID testUUID = UUID.fromString("36296e70-76f1-42af-9272-d39732dcacb0");

    @Override
    public AccountDto getAccount() { // TODO: Security
//        UUID accountId = UUID.fromString("9aba7f16-0673-47e9-b5f4-bcfc207e3fb9");
        return accountMapper.accountToAccountDto(getAccountById(testUUID));
    }

    @Override
    public AccountDto getAccountDtoById(UUID accountId) {
        log.info("Get account by ID: {}", accountId);
        return accountMapper.accountToAccountDto(getAccountById(accountId));
    }

    @Override
    public Account getAccountById(UUID accountId) {
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
    public AccountDto updateAccount(AccountDto accountDto) { // TODO: Security
        log.info("Update account with ID: {}", testUUID);
//        UUID accountId = UUID.fromString("9aba7f16-0673-47e9-b5f4-bcfc207e3fb9");
        Account updatedAccount = getAccountById(testUUID);
        BeanUtils.copyNonNullProperties(accountMapper.accountDtoToAccount(accountDto), updatedAccount);
        updatedAccount.setUpdatedOn(LocalDateTime.now());
        return accountMapper.accountToAccountDto(accountRepository.save(updatedAccount));
    }

    @Override
    @Transactional
    public void deleteAccount() { // TODO: Security
//        UUID accountId = UUID.fromString("1dfa36a4-fef9-4dd8-b080-f385b0756ae4");
        Account updatedAccount = getAccountById(testUUID);
        updatedAccount.setIsDeleted(true);
        accountRepository.save(updatedAccount);
        log.info("Delete account with ID: {}", testUUID);
    }

    @Override
    @Transactional
    public void manageAccountBlock(UUID accountId, boolean block) {  // TODO: Security (только админ)
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new AccountNotFoundException(MessageFormat.format("Account not found for ID: {0}", accountId));
        }

        if (block) {
            try {
                accountRepository.updateBlocked(accountId, true);
                log.error("Account is blocked: {}", accountId);
            } catch (DataIntegrityViolationException e) {
                throw new BadRequestException(
                        MessageFormat.format("Account is already blocked: {0}", accountId));
            }
        } else {
            int unblocked = accountRepository.updateBlocked(accountId, false);
            if (unblocked == 0) {
                throw new BadRequestException(
                        MessageFormat.format("Account is not blocked: {0}", accountId));
            }
            log.info("Account is unblocked: {}", accountId);
        }
    }

    @Override
    public List<UUID> getAllAccountIds() {
        log.info("Get all account IDs");
        return accountRepository.findAllIds();
    }

    @Override
    public PageImpl<AccountDto> getAccountsByTheirId(List<UUID> ids, Pageable page) {
        log.info("Get accounts by IDs");
        Page<Account> accountsPage = accountRepository.findAccountsByIds(ids, page);
        List<AccountDto> accountDtoList = accountMapper.accountListToAccountDtoList(accountsPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
    }

    @Override
    public PageImpl<AccountDto> getAllAccounts(Pageable page) {
        log.info("Get all accounts");
        Page<Account> accountsPage = accountRepository.findAll(page);
        List<AccountDto> accountDtoList = accountMapper.accountListToAccountDtoList(accountsPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
    }

    @Override
    public PageImpl<AccountDto> filterBy(AccountSearchDto accountSearchDto, Pageable page) {
        log.info("Get accounts by filter");
        Page<Account> accountsPage = accountRepository.findAll(AccountSpecification.withFilter(accountSearchDto), page);
        List<AccountDto> accountDtoList = accountMapper.accountListToAccountDtoList(accountsPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
    }

    @Override
    public PageImpl<AccountDto> searchAccount(AccountSearchDto accountSearchDto, Pageable page) {
        log.info("Search account");
//        Page<Account> accountsPage = filterBy(accountSearchDto, page);
//        List<AccountDto> accountDtoList = accountMapper.accountListToAccountDtoList(accountsPage.getContent());
//        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
        return filterBy(accountSearchDto, page);
    }

//    @Override
//    public Account getAccountById(UUID accountId) {
//        log.info("Get account by ID: {}", accountId);
//        return accountRepository.findById(accountId)
//                .orElseThrow(() -> new AccountNotFoundException(
//                        MessageFormat.format("Account not found for ID: {0}", accountId)));
//    }
//
//    @Override
//    public Account createAccount(KafkaAuthEvent kafkaAuthEvent) {
//        if (accountRepository.existsByEmail(kafkaAuthEvent.getEmail())) {
//            throw new AlreadyExistsException(
//                    MessageFormat.format("Аккаунт с email {0} уже существует!", kafkaAuthEvent.getEmail()));
//        }
//        log.info("Create account by email: {}", kafkaAuthEvent.getEmail());
//        return accountRepository.save(accountMapper.kafkaAuthEventToAccount(kafkaAuthEvent));
//    }
//
//    @Override
//    @Transactional
//    public Account updateAccount(UUID accountId, Account account) {
//        log.info("Update account with ID: {}", accountId);
//        Account updatedAccount = getAccountById(accountId);
//        BeanUtils.copyNonNullProperties(account, updatedAccount);
//        updatedAccount.setUpdatedOn(LocalDateTime.now());
//        return accountRepository.save(updatedAccount);
//    }
//
//    @Override
//    @Transactional
//    public void deleteAccount(UUID accountId) {
//        log.info("Delete account with ID: {}", accountId);
//        Account updatedAccount = getAccountById(accountId);
//        updatedAccount.setIsDeleted(true);
//        accountRepository.save(updatedAccount);
//    }
//
//    @Override
//    @Transactional
//    public void manageAccountBlock(UUID accountId, UUID blockedAccountId, boolean block) {
//        if (accountRepository.findById(accountId).isEmpty()) {
//            throw new AccountNotFoundException(MessageFormat.format("Account not found for ID: {0}", accountId));
//        }
//        if (accountRepository.findById(blockedAccountId).isEmpty()) {
//            throw new AccountNotFoundException(MessageFormat.format("Account not found for ID: {0}", blockedAccountId));
//        }
//
//        if (block) {
//            try {
//                BlockAccount blockAccount = BlockAccount.builder()
//                        .accountId(accountId)
//                        .blockedAccountId(blockedAccountId)
//                        .build();
//                blockAccountRepository.save(blockAccount);
//                log.error("Account is blocked: {} -> {}", accountId, blockedAccountId);
//            } catch (DataIntegrityViolationException e) {
//                /* Как его убрать?
//                ERROR: duplicate key value violates unique constraint "uq_account_blocked"
//                Подробности: Key (account_id, blocked_account_id)=(4b7bbd0c-10db-4e8e-9a3d-7556137c2601, 9ab07a7f-cd14-4780-b575-f6aa4a6a45f3) already exists.
//                */
//                throw new BadRequestException(
//                        MessageFormat.format("Account is already blocked: {0} -> {1}", accountId, blockedAccountId));
//            }
//        } else {
//            int unblocked = blockAccountRepository.unblockAccount(accountId, blockedAccountId);
//            if (unblocked == 0) {
//                throw new BadRequestException(
//                        MessageFormat.format("Account is not blocked: {0} -> {1}", accountId, blockedAccountId));
//            }
//            log.info("Account is unblocked: {} -> {}", accountId, blockedAccountId);
//        }
//    }
//
//    @Override
//    public List<UUID> getAllAccountIds() {
//        log.info("Get all account IDs");
//        return accountRepository.findAllIds();
//    }
//
//    @Override
//    public List<Account> getAccountsByTheirId(List<UUID> ids, Pageable pageable) {
//        log.info("Get accounts by IDs");
//        return accountRepository.findAccountsByIds(ids, pageable).getContent();
//    }
//
//    @Override
//    public List<Account> getAllAccounts(Pageable pageable) {
//        log.info("Get all accounts");
//        return accountRepository.findAll(pageable).getContent();
//    }
//
//    @Override
//    public Page<Account> filterBy(AccountSearchDto accountSearchDto, Pageable pageable) {
//        log.info("Get accounts by filter");
//        return accountRepository.findAll(AccountSpecification.withFilter(accountSearchDto), pageable);
//    }
//
//    @Override
//    public List<Account> searchAccount(AccountSearchDto accountSearchDto) {
//        log.info("Search account");
//        return List.of();
//    }

}
