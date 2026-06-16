package ru.istok.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import ru.istok.backend.config.JwtProperties;
import ru.istok.backend.user.entity.User;
import ru.istok.backend.user.entity.UserRole;

@Service
public class JwtService {

    private final JwtProperties properties;
    private final SecretKey key;

    public JwtService(JwtProperties properties) {
        this.properties = properties;
        this.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(properties.getExpiration());

        return Jwts.builder()
                .subject(user.getLogin())
                .claims(Map.of(
                        "userId", user.getId(),
                        "login", user.getLogin(),
                        "role", user.getRole().name()
                ))
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(key)
                .compact();
    }

    public JwtUser parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return new JwtUser(
                    claims.get("userId", Long.class),
                    claims.get("login", String.class),
                    UserRole.valueOf(claims.get("role", String.class))
            );
        } catch (IllegalArgumentException | JwtException exception) {
            throw new InvalidJwtException();
        }
    }
}
