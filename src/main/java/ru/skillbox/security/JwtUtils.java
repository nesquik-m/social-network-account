package ru.skillbox.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.skillbox.entity.RoleType;

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

    private String decodeToken(String token) {
        String[] parts = token.split("\\.");
        String payload = parts[1];
        byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
        return new String(decodedBytes);
    }

    private Map<String, Object> getClaimsFromToken(String token) {
        String payload = decodeToken(token);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> claims = null;
        try {
            claims = objectMapper.readValue(payload, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return claims;
    }

    public String getUUIDFromToken(String token) {
        return (String) getClaimsFromToken(token).get("id");
    }

    public List<RoleType> getRolesFromToken(String token) {
        List<String> rolesAsString = (List<String>) getClaimsFromToken(token).get("roles");
        return rolesAsString.stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toList());
    }
}
