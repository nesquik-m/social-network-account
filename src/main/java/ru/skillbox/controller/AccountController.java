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
import ru.skillbox.service.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/recovery")
    public String accountRecovery(@RequestBody AccountRecoveryRq accountRecoveryRq) { // TODO: Security
        // TODO: Логика восстановления аккаунта: Сервис авторизации?
        return "Successfully";
        // 200 (+ "Successfully"), 400, 401, 404
    }

    @GetMapping("/me") // +
    public AccountDto getAccount() {
        return accountService.getAccount();
        // 200 (+ объект AccountDto!), 400, 401
    }

    @PutMapping("/me") // +
    public AccountDto updateAccount(@RequestBody @Valid AccountDto accountDto) {
        return accountService.updateAccount(accountDto);
        // 200 (+ объект AccountDto!), 400, 401, 404
    }

    @DeleteMapping // +
    public String deleteAccount() {
        accountService.deleteAccount();
        return "Successfully";
        // 200 (+ "Successfully"), 400, 401, 404
    }

    @PutMapping("/block/{id}") // +
    public String blockAccount(@PathVariable("id") UUID accountId) {
        accountService.manageAccountBlock(accountId, true);
        return "Successfully";
        // 200 (+ "Successfully"), 400, 401, 404
    }

    @DeleteMapping("/block/{id}") // +
    public String unblockAccount(@PathVariable("id") UUID accountId) {
        accountService.manageAccountBlock(accountId, false);
        return "Successfully";
        // 200 (+ "Successfully"), 400, 401, 404
    }

    @GetMapping // +
    public PageImpl<AccountDto> getAllAccounts(@PageableDefault(sort = "firstName", direction = Sort.Direction.ASC) Pageable page) {
        return accountService.getAllAccounts(page);
        // возвращать 200, 400, 401
    }

    @GetMapping("/{id}") // +
    public AccountDto getAccountById(@PathVariable("id") UUID accountId) {
        return accountService.getAccountDtoById(accountId);
        // 200 (+ объект AccountDto), 400, 401
    }

    @PostMapping("/searchByFilter") // +
    public PageImpl<AccountDto> searchAccountByFilter(@RequestBody @Valid AccountByFilterDto filterDto) {
        return accountService.filterBy(filterDto.getAccountSearchDto(),
                PageRequest.of(filterDto.getPageNumber(), filterDto.getPageSize()));
        // 200 (+ объект AccountDto), 400, 401
    }

    // {{8080}}/api/v1/account/search?author=Abagael Jolliss&page=0&size=10&sort=lastName,DESC
    // {{8080}}/api/v1/account/search?author=Abagael%20Jolliss&page=0&size=10&sort=lastName,DESC
    @GetMapping("/search") // +
    public PageImpl<AccountDto> searchAccount(@ModelAttribute AccountSearchDto dto,
                                              @PageableDefault(sort = "firstName", direction = Sort.Direction.ASC) Pageable page) {
        return accountService.searchAccount(dto, page);
        // 200, 400, 401
    }

    @GetMapping("/ids") // +
    public List<UUID> getAllAccountIds() {
        return accountService.getAllAccountIds();
        // 200 (+ List<UUID>), 400, 401
    }

    @GetMapping("/accountIds") // +
    public PageImpl<AccountDto> getAccountsByTheirIds(@RequestParam List<UUID> ids,
                                                  @PageableDefault(sort = "firstName", direction = Sort.Direction.ASC) Pageable page) {
        return accountService.getAccountsByTheirId(ids, page);
        // 200 (+ List<Account> + pageable), 400, 401
    }

}

