package ru.lopa10ko.cats.security.services;

import io.jsonwebtoken.Claims;
import ru.lopa10ko.cats.entities.User;

import java.util.function.Function;
public interface JwtService {
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateToken(User user);
    boolean isTokenValid(String token);
    boolean isTokenExpired(String token);
    Claims extractAllClaims(String token);
}
