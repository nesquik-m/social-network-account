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
import ru.skillbox.entity.Account;
import ru.skillbox.entity.RoleType;
import ru.skillbox.service.AccountService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

//    private final UserDetailsServiceImpl userDetailsService;

//    private final UUID testUUID = UUID.fromString("3c3925ea-3c3b-4361-96df-d6eb98c072d0");

//    private final AccountRepository accountRepository;
    private final AccountService accountService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = getToken(request);

            if (jwtToken != null && jwtUtils.validateToken(jwtToken)) {
                System.out.println("ТОКЕН: " + jwtToken);

                String id = jwtUtils.getUUIDFromToken(jwtToken);
                System.out.println(id);

                List<RoleType> roles = jwtUtils.getRolesFromToken(jwtToken);
                System.out.println(roles);

                Account account = accountService.getAccountById(UUID.fromString(id));
                account.setRoles(roles);
                System.out.println(account);

                UserDetails userDetails = new AppUserDetails(account);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
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
