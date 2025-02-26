package com.challengerstory.chattingstory.security.infrastructure.jwt;

import com.challengerstory.chattingstory.security.domain.aggregate.CustomUser;
import com.challengerstory.chattingstory.security.application.service.AuthUserService;
import com.challengerstory.chattingstory.security.config.CustomSecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final AuthUserService authUserService;
    private final JwtUtil jwtUtil;
    private final CustomSecurityProperties customSecurityProperties;

    public String generateAccessToken(CustomUser user){
        return buildToken(user, customSecurityProperties.getAccessExpirationTime());
    }

    public String generateRefreshToken(CustomUser user){
        return buildToken(user, customSecurityProperties.getRefreshExpirationTime());
    }

    /* subject: email(OAuth 유저의 경우 userLogin@UserType.com 형식으로 저장) */
    public String buildToken(CustomUser user, Long expiration){
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("auth", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        
        return jwtUtil.signToken(Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration)));
    }

}
