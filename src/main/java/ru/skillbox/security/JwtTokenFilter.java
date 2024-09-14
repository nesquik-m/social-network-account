package ru.skillbox.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

//    private final JwtUtils jwtUtils;

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = getToken(request); // Здесь мы получаем токен из http-заголовка.

//            if (jwtToken != null && jwtUtils.validate(jwtToken)) { // Проверяем, что он представлен в заголовке и валиден.
            if (jwtToken != null) { // Проверяем, что он представлен в заголовке и валиден.
                System.out.println("ТОКЕН: " + jwtToken);

//                String username = jwtUtils.getUsername(jwtToken);
                String accountId = String.valueOf(UUID.randomUUID()); // TODO: << ЗДЕСЬ НУЖНО ПОЛУЧИТЬ ИЗ ТОКЕНА UUID
                UserDetails userDetails = userDetailsService.loadUserByUsername(accountId); // Ищем пользователя в БД

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication); // Помещаем информацию о пользователе в контекст!
            }
        } catch (Exception e) {
            log.error("Cannot set account authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response); // проталкивание запроса дальше по конвейеру
    }

    private String getToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Первые 7 символов
        }

        return null;
    }
}
