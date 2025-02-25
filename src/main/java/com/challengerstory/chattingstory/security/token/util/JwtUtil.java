package com.challengerstory.chattingstory.security.token.util;

import com.challengerstory.chattingstory.user.command.application.service.AppUserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
    private final Key secretKey;
    private final AppUserService appUserService;

    public JwtUtil(@Value("${security.secret}") String secret, AppUserService appUserService) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.appUserService = appUserService;
    }

    public String signToken(JwtBuilder builder) {
        return builder.signWith(secretKey, SignatureAlgorithm.HS512).compact();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
     *  설명.
     *   access token : userType + userLogin
     *   refresh token: email
     * */
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().before(new Date());
        } catch(ExpiredJwtException e){
            return true;
        }
    }

    public Long getRemainingTime(@NotNull String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration().getTime() - new Date().getTime();
    }


}
