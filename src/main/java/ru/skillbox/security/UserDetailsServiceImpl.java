//package ru.skillbox.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//import ru.skillbox.entity.Account;
//import ru.skillbox.exception.AccountNotFoundException;
//import ru.skillbox.repository.AccountRepository;
//
//import java.text.MessageFormat;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final AccountRepository accountRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String accountId) throws AccountNotFoundException {
//        Account account = accountRepository.findById(UUID.fromString(accountId))
//                .orElseThrow(() -> new AccountNotFoundException(
//                        MessageFormat.format("Account not found for ID: {0}", accountId)
//                ));
//        return new AppUserDetails(account);
//    }
//
//}
