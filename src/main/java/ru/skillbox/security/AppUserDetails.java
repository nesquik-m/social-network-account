package ru.skillbox.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skillbox.entity.Account;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {

    private final Account account;

    public UUID getId() {
        return account.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return account.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
