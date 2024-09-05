package ru.skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.aop.LogAspect;
import ru.skillbox.aop.LogType;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.dto.AccountSearchDto;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;
import ru.skillbox.exception.AccountNotFoundException;
import ru.skillbox.exception.AlreadyExistsException;
import ru.skillbox.exception.BadRequestException;
import ru.skillbox.mapper.AccountMapper;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.repository.specification.AccountSpecification;
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

    private final AccountMapper accountMapper;

    private final UUID testUUID = UUID.fromString("5bd73891-a00e-44e9-954c-f6610d4d1a16");

    @Override
    @LogAspect(type = LogType.SERVICE)
    public AccountDto getAccount() { // TODO: Security
        return accountMapper.accountToAccountDto(getAccountById(testUUID));
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public AccountDto getAccountDtoById(UUID accountId) {
        return accountMapper.accountToAccountDto(getAccountById(accountId));
    }

    private Account getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        MessageFormat.format("Account not found for ID: {0}", accountId)));
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public Account createAccount(KafkaAuthEvent kafkaAuthEvent) {
        if (accountRepository.existsByEmail(kafkaAuthEvent.getEmail())) {
            throw new AlreadyExistsException(
                    MessageFormat.format("Аккаунт с email {0} уже существует!", kafkaAuthEvent.getEmail()));
        }
        return accountRepository.save(accountMapper.kafkaAuthEventToAccount(kafkaAuthEvent));
    }

    @Override
    @Transactional
    @LogAspect(type = LogType.SERVICE)
    public AccountDto updateAccount(AccountDto accountDto) { // TODO: Security
        Account updatedAccount = getAccountById(testUUID);

        // BeanUtils.copyNonNullProperties(accountMapper.accountDtoToAccount(accountDto), updatedAccount);
        // id
        // isBlocked;
        // isDeleted;
        // isOnline;
        // createdOn;
        // updatedOn;

        if (accountDto.getEmail() != null && !accountDto.getEmail().isEmpty()) {
            updatedAccount.setEmail(accountDto.getEmail());
        }
        if (accountDto.getCity() != null && !accountDto.getCity().isEmpty()) {
            updatedAccount.setCity(accountDto.getCity());
        }
        if (accountDto.getCountry() != null && !accountDto.getCountry().isEmpty()) {
            updatedAccount.setCountry(accountDto.getCountry());
        }
        if (accountDto.getFirstName() != null && !accountDto.getFirstName().isEmpty()) {
            updatedAccount.setFirstName(accountDto.getFirstName());
        }
        if (accountDto.getLastName() != null && !accountDto.getLastName().isEmpty()) {
            updatedAccount.setLastName(accountDto.getLastName());
        }
        if (accountDto.getBirthDate() != null) {
            updatedAccount.setBirthDate(accountDto.getBirthDate());
        }
        if (accountDto.getPhone() != null && !accountDto.getPhone().isEmpty()) {
            updatedAccount.setPhone(accountDto.getPhone());
        }
        if (accountDto.getAbout() != null && !accountDto.getAbout().isEmpty()) {
            updatedAccount.setAbout(accountDto.getAbout());
        }
        if (accountDto.getProfileCover() != null && !accountDto.getProfileCover().isEmpty()) {
            updatedAccount.setProfileCover(accountDto.getProfileCover());
        }
        updatedAccount.setUpdatedOn(LocalDateTime.now());

        return accountMapper.accountToAccountDto(accountRepository.save(updatedAccount));
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
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new AccountNotFoundException(MessageFormat.format("Account not found for ID: {0}", accountId));
        }

        if (block) {
            try {
                accountRepository.updateBlocked(accountId, true);
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
        List<AccountDto> accountDtoList = accountMapper.accountListToAccountDtoList(accountsPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public PageImpl<AccountDto> getAllAccounts(Pageable page) {
        Page<Account> accountsPage = accountRepository.findAll(page);
        List<AccountDto> accountDtoList = accountMapper.accountListToAccountDtoList(accountsPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public PageImpl<AccountDto> filterBy(AccountSearchDto accountSearchDto, Pageable page) {
        Page<Account> accountsPage = accountRepository.findAll(AccountSpecification.withFilter(accountSearchDto), page);
        List<AccountDto> accountDtoList = accountMapper.accountListToAccountDtoList(accountsPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountsPage.getTotalElements());
    }

    @Override
    @LogAspect(type = LogType.SERVICE)
    public PageImpl<AccountDto> searchAccount(AccountSearchDto accountSearchDto, Pageable page) {
        return filterBy(accountSearchDto, page);
    }

}
