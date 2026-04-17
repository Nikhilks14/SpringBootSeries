package com.practice.SpringBoot.Services;

import com.practice.SpringBoot.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    };

    public String generateAcessToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail()) // Claim (key,value)
//                .claim("roles", Set.of("ADMIN", "USER"))
                .claim("roles", user.getRoles().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 ))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L *60 *60*24*6))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())   // ← same as getUserIdFromToken
                    .build()
                    .parseSignedClaims(token);    // ← same as getUserIdFromToken
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token expired: {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            log.warn("Token malformed: {}", e.getMessage());
            return false;
        } catch (SignatureException e) {
            log.warn("Token signature invalid: {}", e.getMessage());
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Token invalid ({}): {}", e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }
}
