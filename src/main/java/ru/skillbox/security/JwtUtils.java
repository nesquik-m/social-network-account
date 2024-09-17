package ru.skillbox.security;

import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.skillbox.entity.RoleType;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {

    public boolean validateToken(String token) {
        // TODO This is a stub!!!
        // Must be something like this:
        //    return restTemplate.exchange(".../api/v1/auth/confirmtoken?token={}",
        //          HttpMethod.GET,
        //          null,
        //          Boolean.class,
        //          token).getBody();
        return true;
    }

    public String getIdFromToken(String token) throws ParseException {
        return SignedJWT.parse(token).getPayload().toJSONObject().get("id").toString();
    }

    public List<RoleType> getRolesFromToken(String token) throws ParseException {
        List<String> roles = (List<String>) SignedJWT.parse(token).getPayload().toJSONObject().get("roles");
        return roles.stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toList());
    }
}
