package com.bookmarket.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public Claims parseJwt(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = parseJwt(token);
            return claims != null && !isTokenExpired(claims);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseJwt(token);
        return claims != null ? claims.getSubject() : null;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = parseJwt(token);
        if (claims != null && claims.get("userId") != null) {
            return claims.get("userId", Long.class);
        }
        return null;
    }

    private boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        System.out.println("expiration: 检测是否过期");
        System.out.println(expiration.before(new Date()));
        return expiration != null && expiration.before(new Date());
    }
}