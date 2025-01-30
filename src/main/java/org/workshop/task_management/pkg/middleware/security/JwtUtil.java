package org.workshop.task_management.pkg.middleware.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class JwtUtil {

    @Autowired
    private Environment environment;

    public String generateToken(String userID) {
        String secretKey = environment.getProperty("JWT_SECRET");

        Key SECRET_KEY_INSTANCE = Keys.hmacShaKeyFor(Objects.requireNonNull(secretKey).getBytes());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10); // Token valid for 10 hours
        return Jwts.builder()
                .claim("sub", userID)
                .expiration(expiration)
                .issuedAt(new Date())
                .signWith(SECRET_KEY_INSTANCE)
                .compact();
    }

    public String isTokenValid(String token) {
        try {
            String secretKey = environment.getProperty("JWT_SECRET");
            SecretKey secretKeyInstance = Keys.hmacShaKeyFor(Objects.requireNonNull(secretKey).getBytes());
            return Jwts.parser()
                    .verifyWith(secretKeyInstance)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload().getSubject();
        } catch (Exception e) {
            return "";
        }
    }
}