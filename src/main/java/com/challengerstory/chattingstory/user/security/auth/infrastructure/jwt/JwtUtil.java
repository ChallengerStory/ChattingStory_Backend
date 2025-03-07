package com.challengerstory.chattingstory.user.security.auth.infrastructure.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final Key secretKey;

    public JwtUtil(@Value("${security.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String signToken(JwtBuilder builder) {
        return builder.signWith(secretKey, SignatureAlgorithm.HS512).compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /* subject: email */
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public void isTokenValid(String token){
        parseClaims(token);
    }

    public Long getRemainingTime(@NotNull String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration().getTime() - new Date().getTime();
    }


}
