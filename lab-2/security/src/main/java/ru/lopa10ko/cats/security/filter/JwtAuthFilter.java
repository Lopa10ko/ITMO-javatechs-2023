package ru.lopa10ko.cats.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.lopa10ko.cats.security.model.CatsAuthentication;
import ru.lopa10ko.cats.security.model.Roles;
import ru.lopa10ko.cats.security.services.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtServiceImpl;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ") || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        if (!jwtServiceImpl.isTokenValid(jwt) || jwtServiceImpl.isTokenExpired(jwt)) {
            filterChain.doFilter(request, response);
        }
        String userName = jwtServiceImpl.extractUsername(jwt);
        UUID id = Optional.ofNullable(jwtServiceImpl.extractClaim(jwt, claims -> claims.get("ownerUuid", String.class)))
                .map(UUID::fromString)
                .orElseThrow(RuntimeException::new);
        @SuppressWarnings("unchecked")
        Set<Roles> roles = Stream.ofNullable(jwtServiceImpl.extractClaim(jwt, claims -> claims.get("roles", List.class)))
                .flatMap(List<String>::stream)
                .map(Roles::valueOf)
                .collect(Collectors.toSet());
        CatsAuthentication catsAuthentication = new CatsAuthentication(userName, roles, id, true);
        SecurityContextHolder.getContext().setAuthentication(catsAuthentication);
        filterChain.doFilter(request, response);
    }
}

