package ru.skillbox.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.dto.AccountByFilterDto;
import ru.skillbox.dto.AccountDto;
import ru.skillbox.dto.AccountRecoveryRq;
import ru.skillbox.dto.AccountSearchDto;
import ru.skillbox.entity.Account;
import ru.skillbox.mapper.AccountMapper;
import ru.skillbox.service.AccountService;

import java.util.List;
import java.util.UUID;

    /*
    ошибка 404 будет возвращена при проверке наличия аккаунта в БД
    */

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @PutMapping("/recovery")
    public String accountRecovery(@RequestBody AccountRecoveryRq accountRecoveryRq) {
        log.info("AccountController - accountRecovery: {}", accountRecoveryRq);
        // TODO: Логика восстановления аккаунта: Сервис авторизации?
        return "Successfully";
        // 200 (+ "Successfully"), 400, 401, 404
    }

    @GetMapping("/me") // +
    public AccountDto getAccount() {
        log.info("AccountController - getAccount");
        UUID accountId = UUID.fromString("9aba7f16-0673-47e9-b5f4-bcfc207e3fb9");
        Account currentAccount = accountService.getAccountById(accountId);
        return accountMapper.accountToAccountDto(currentAccount);
        // 200 (+ объект AccountDto!), 400, 401
    }

    @PutMapping("/me")
    public AccountDto updateAccount(@RequestBody @Valid AccountDto accountDto) {
        log.info("AccountController - updateAccount");
        UUID accountId = UUID.fromString("9aba7f16-0673-47e9-b5f4-bcfc207e3fb9");
        Account updatedAccount = accountService.updateAccount(accountId, accountMapper.accountDtoToAccount(accountDto));
        return accountMapper.accountToAccountDto(updatedAccount);
        // 200 (+ объект AccountDto!), 400, 401, 404
    }

    @DeleteMapping // +
    public String deleteAccount() {
        log.info("AccountController - deleteAccount");
        UUID accountId = UUID.fromString("1dfa36a4-fef9-4dd8-b080-f385b0756ae4");
        accountService.deleteAccount(accountId); // возвращает 404
        return "Successfully";
        // 200 (+ "Successfully"), 400, 401, 404
    }

    @PutMapping("/block/{id}") // +
    public String blockAccount(@PathVariable("id") UUID accountId) {
        log.info("AccountController - blockAccount");
        accountService.updateBlocked(accountId, true);
        return "Successfully";
        // 200 (+ "Successfully"), 400, 401, 404
    }

    @DeleteMapping("/block/{id}") // +
    public String unblockAccount(@PathVariable("id") UUID accountId) {
        // TODO: Логика разблокировки аккаунта: Сервис авторизации?
        log.info("AccountController - unblockAccount");
        accountService.updateBlocked(accountId, false);
        return "Successfully";
        // 200 (+ "Successfully"), 400, 401, 404
    }

    @GetMapping // +
    public List<AccountDto> getAllAccounts(@PageableDefault(sort = "firstName", direction = Sort.Direction.ASC) Pageable page) {
        log.info("AccountController - getAllAccounts");
        List<Account> accounts = accountService.getAllAccounts(page);
        return accountMapper.accountListToAccountDtoList(accounts);
        // возвращать 200, 400, 401
    }

    @GetMapping("/{id}") // +
    public AccountDto getAccountById(@PathVariable("id") UUID accountId) {
        log.info("AccountController - getAccountById");
        return accountMapper.accountToAccountDto(accountService.getAccountById(accountId));
        // 200 (+ объект AccountDto), 400, 401
    }

    @PostMapping("/searchByFilter") // +
    public List<AccountDto> searchAccountByFilter(@RequestBody @Valid AccountByFilterDto filterDto) {
        log.info("AccountController - searchAccountByFilter");
        Page<Account> accounts = accountService.filterBy(filterDto.getAccountSearchDto(),
                PageRequest.of(filterDto.getPageNumber(), filterDto.getPageSize()));
        return accountMapper.accountListToAccountDtoList(accounts.getContent());
        // 200 (+ объект AccountDto), 400, 401
    }

    // {{8080}}/api/v1/account/search?author=Abagael Jolliss&page=0&size=10&sort=lastName,DESC
    // {{8080}}/api/v1/account/search?author=Abagael%20Jolliss&page=0&size=10&sort=lastName,DESC
    @GetMapping("/search") // +
    public PageImpl<AccountDto> searchAccount(
            @ModelAttribute AccountSearchDto dto,
            @PageableDefault(sort = "firstName", direction = Sort.Direction.ASC) Pageable page) {
        log.info("AccountController - searchAccount");
        Page<Account> accountPage = accountService.filterBy(dto, page);
        List<AccountDto> accountDtoList = accountMapper.accountListToAccountDtoList(accountPage.getContent());
        return new PageImpl<>(accountDtoList, page, accountPage.getTotalElements());
        // 200, 400, 401
    }

    @GetMapping("/ids") // +
    public List<UUID> getAllAccountIds() {
        log.info("AccountController - getAllAccountIds");
        return accountService.getAllAccountIds();
        // 200 (+ List<UUID>), 400, 401
    }

    @GetMapping("/accountIds") // +
    public List<AccountDto> getAccountsByTheirId(@RequestParam List<UUID> ids,
                                                 @PageableDefault(sort = "firstName", direction = Sort.Direction.ASC) Pageable page) {
        log.info("AccountController - getAccountsByTheirId");
        List<Account> accounts = accountService.getAccountsByTheirId(ids, page);
        return accountMapper.accountListToAccountDtoList(accounts);
        // 200 (+ List<Account> + pageable), 400, 401
    }

}

