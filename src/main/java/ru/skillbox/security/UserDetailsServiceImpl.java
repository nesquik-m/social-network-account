package ru.skillbox.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String token) {
        return new User(
                jwtUtils.getUUIDFromToken(token),
                "password",
                true,
                true,
                true,
                true,
                jwtUtils.getRolesFromToken(token).stream()
                        .map(r -> new SimpleGrantedAuthority(r.name())).toList()
        );
    }
}
