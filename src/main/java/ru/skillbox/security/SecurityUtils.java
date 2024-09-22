package ru.skillbox.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import ru.skillbox.exception.BadRequestException;

import java.util.UUID;

public class SecurityUtils {

    public static UUID getUUIDFromSecurityContext() {
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof User user) {
            return UUID.fromString(user.getUsername());
        }
        throw new BadRequestException("Account id is null!");
    }

}

