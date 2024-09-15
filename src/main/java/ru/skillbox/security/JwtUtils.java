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
//        for (Map.Entry<String, Object> entry : claims.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
        return claims;
    }

    public String getUUIDFromToken(String token) {
        return (String) getClaimsFromToken(token).get("id");
    }

    //    public Set<RoleType> getRolesFromToken(String token) {
//        List<RoleType> roles = (List<RoleType>) getClaimsFromToken(token).get("roles");
//        return new HashSet<>(roles);
//    }
//    public List<RoleType> getRolesFromToken(String token) {
//        return (List<RoleType>) getClaimsFromToken(token).get("roles");
//    }

    public List<RoleType> getRolesFromToken(String token) {
        // Получаем список ролей из токена
        List<String> rolesAsString = (List<String>) getClaimsFromToken(token).get("roles");

        // Преобразуем список строк в список RoleType
        return rolesAsString.stream()
                .map(RoleType::valueOf) // Преобразование строки в RoleType
                .collect(Collectors.toList());
    }
}
