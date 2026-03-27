package com.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // SECRET KEY (same for generate & validate)
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // ===============================
    // GENERATE TOKEN
    // ===============================
    public static String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60) // 1 hour
                )
                .signWith(key)
                .compact();
    }

    // ===============================
    // VALIDATE TOKEN + EXTRACT EMAIL
    // ===============================
    public static String extractEmail(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // email
    }
}